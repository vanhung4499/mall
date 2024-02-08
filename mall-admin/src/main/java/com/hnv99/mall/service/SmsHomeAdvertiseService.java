package com.hnv99.mall.service;

import com.hnv99.mall.model.SmsHomeAdvertise;

import java.util.List;

public interface SmsHomeAdvertiseService {
    /**
     * Add advertisement
     */
    int create(SmsHomeAdvertise advertise);

    /**
     * Batch delete advertisements
     */
    int delete(List<Long> ids);

    /**
     * Modify online/offline status
     */
    int updateStatus(Long id, Integer status);

    /**
     * Get advertisement details
     */
    SmsHomeAdvertise getItem(Long id);

    /**
     * Update advertisement
     */
    int update(Long id, SmsHomeAdvertise advertise);

    /**
     * Page query advertisements
     */
    List<SmsHomeAdvertise> list(String name, Integer type, String endTime, Integer pageSize, Integer pageNum);
}
