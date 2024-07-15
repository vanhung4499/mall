package com.union.mall.system.service.impl.oss;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.union.mall.system.model.vo.FileInfoVO;
import com.union.mall.system.service.OssService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@ConditionalOnProperty(value = "oss.type", havingValue = "minio")
@ConfigurationProperties(prefix = "oss.minio")
@RequiredArgsConstructor
@Data
public class MinioOssService implements OssService {

    /**
     * Service Endpoint (http://localhost:9000)
     */
    private String endpoint;

    /**
     * Access Key
     */
    private String accessKey;

    /**
     * Secret Key
     */
    private String secretKey;

    /**
     * Bucket Name
     */
    private String bucketName;

    /**
     * Custom Domain (https://oss.union.mall.tech)
     */
    private String customDomain;

    private MinioClient minioClient;

    // Initialization after dependency injection
    @PostConstruct
    public void init() {
        minioClient = MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }

    /**
     * Upload File
     *
     * @param file Form file object
     * @return
     */
    @Override
    public FileInfoVO uploadFile(MultipartFile file) {
        // Generate file name (date folder)
        String suffix = FileUtil.getSuffix(file.getOriginalFilename());
        String uuid = IdUtil.simpleUUID();
        String fileName = DateUtil.format(LocalDateTime.now(), "yyyyMMdd") + "/" + uuid + "." + suffix;

        try (InputStream inputStream = file.getInputStream()) {
            // File upload
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .contentType(file.getContentType())
                    .stream(inputStream, inputStream.available(), -1)
                    .build();
            minioClient.putObject(putObjectArgs);

            // Return file path
            String fileUrl;
            if (StrUtil.isBlank(customDomain)) { // Custom domain not configured
                GetPresignedObjectUrlArgs getPresignedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                        .bucket(bucketName).object(fileName)
                        .method(Method.GET)
                        .build();
                fileUrl = minioClient.getPresignedObjectUrl(getPresignedObjectUrlArgs);
                fileUrl = fileUrl.substring(0, fileUrl.indexOf("?"));
            } else { // Custom domain configured
                fileUrl = customDomain + '/' + bucketName + "/" + fileName;
            }

            FileInfoVO fileInfo = new FileInfoVO();
            fileInfo.setName(fileName);
            fileInfo.setUrl(fileUrl);
            return fileInfo;
        } catch (Exception e) {
            throw new RuntimeException("File upload failed");
        }
    }

    /**
     * Delete File
     *
     * @param filePath File path
     *                 https://oss.union.mall.tech/default/20221120/test.jpg
     * @return
     */
    @Override
    public boolean deleteFile(String filePath) {
        Assert.notBlank(filePath, "File path to delete cannot be blank");
        try {
            String fileName;
            if (StrUtil.isNotBlank(customDomain)) {
                // https://oss.union.mall.tech/default/20221120/test.jpg → 20221120/test.jpg
                fileName = filePath.substring(customDomain.length() + 1 + bucketName.length() + 1); // Two '/' take up 2 characters
            } else {
                // http://localhost:9000/default/20221120/test.jpg → 20221120/test.jpg
                fileName = filePath.substring(endpoint.length() + 1 + bucketName.length() + 1);
            }
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .build();
            minioClient.removeObject(removeObjectArgs);
            return true;
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException |
                 InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException |
                 XmlParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * PUBLIC Bucket Policy
     * If not configured, newly created buckets default to PRIVATE, and bucket files will reject access (Access Denied)
     *
     * @param bucketName
     * @return
     */
    private static String publicBucketPolicy(String bucketName) {
        /**
         * AWS S3 Bucket Policy
         * Principal: User object that takes effect
         * Resource: Specifies the bucket
         * Action: Action performed
         */
        return "{\"Version\":\"2012-10-17\","
                + "\"Statement\":[{\"Effect\":\"Allow\","
                + "\"Principal\":{\"AWS\":[\"*\"]},"
                + "\"Action\":[\"s3:ListBucketMultipartUploads\",\"s3:GetBucketLocation\",\"s3:ListBucket\"],"
                + "\"Resource\":[\"arn:aws:s3:::" + bucketName + "\"]},"
                + "{\"Effect\":\"Allow\"," + "\"Principal\":{\"AWS\":[\"*\"]},"
                + "\"Action\":[\"s3:ListMultipartUploadParts\",\"s3:PutObject\",\"s3:AbortMultipartUpload\",\"s3:DeleteObject\",\"s3:GetObject\"],"
                + "\"Resource\":[\"arn:aws:s3:::" + bucketName + "/*\"]}]}";
    }

    /**
     * Create Bucket (if bucket does not exist)
     *
     * @param bucketName
     */
    @SneakyThrows
    private void createBucketIfAbsent(String bucketName) {
        BucketExistsArgs bucketExistsArgs = BucketExistsArgs.builder().bucket(bucketName).build();
        if (!minioClient.bucketExists(bucketExistsArgs)) {
            MakeBucketArgs makeBucketArgs = MakeBucketArgs.builder().bucket(bucketName).build();

            minioClient.makeBucket(makeBucketArgs);

            // Set bucket access policy to PUBLIC, if not configured, newly created buckets default to PRIVATE, and bucket files will reject access (Access Denied)
            SetBucketPolicyArgs setBucketPolicyArgs = SetBucketPolicyArgs
                    .builder()
                    .bucket(bucketName)
                    .config(publicBucketPolicy(bucketName))
                    .build();
            minioClient.setBucketPolicy(setBucketPolicyArgs);
        }
    }
}
