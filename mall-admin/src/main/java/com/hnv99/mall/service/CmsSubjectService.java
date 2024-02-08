package com.hnv99.mall.service;

import com.hnv99.mall.model.CmsSubject;

import java.util.List;

public interface CmsSubjectService {
    /**
     * Query all subjects
     */
    List<CmsSubject> listAll();

    /**
     * Paginated query of subjects
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
