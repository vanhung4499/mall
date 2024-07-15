package com.union.mall.system.service;

import com.union.mall.system.model.vo.FileInfoVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Object storage service interface layer
 *
 * @author vanhung4499
 */
public interface OssService {

    /**
     * Uploads a file.
     *
     * @param file Form file object
     * @return File information
     */
    FileInfoVO uploadFile(MultipartFile file);

    /**
     * Deletes a file.
     *
     * @param filePath Full URL of the file
     * @return Deletion result
     */
    boolean deleteFile(String filePath);

}
