package com.hnv99.mall.service;

import com.hnv99.mall.dto.OssCallbackResult;
import com.hnv99.mall.dto.OssPolicyResult;

import javax.servlet.http.HttpServletRequest;

public interface OssService {
    /**
     * Generate Oss upload policy
     */
    OssPolicyResult policy();

    /**
     * Oss upload success callback
     */
    OssCallbackResult callback(HttpServletRequest request);
}
