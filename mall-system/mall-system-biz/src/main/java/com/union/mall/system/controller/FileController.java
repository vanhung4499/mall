package com.union.mall.system.controller;

import com.union.mall.common.core.result.Result;
import com.union.mall.system.model.vo.FileInfoVO;
import com.union.mall.system.service.OssService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "06. File API")
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final OssService ossService;

    @PostMapping
    @Operation(summary = "File Upload")
    public Result<FileInfoVO> uploadFile(
            @Parameter(description = "Form file object") @RequestParam(value = "file") MultipartFile file
    ) {
        FileInfoVO fileInfo = ossService.uploadFile(file);
        return Result.success(fileInfo);
    }

    @DeleteMapping
    @Operation(summary = "File Delete")
    public Result deleteFile(
            @Parameter(description = "File path") @RequestParam String filePath
    ) {
        boolean result = ossService.deleteFile(filePath);
        return Result.judge(result);
    }

}
