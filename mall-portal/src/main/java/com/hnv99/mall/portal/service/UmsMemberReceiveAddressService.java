package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.UmsMemberReceiveAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UmsMemberReceiveAddressService {
    /**
     * Add a delivery address
     */
    int add(UmsMemberReceiveAddress address);

    /**
     * Delete a delivery address
     * @param id ID of the address in the table
     */
    int delete(Long id);

    /**
     * Update a delivery address
     * @param id ID of the address in the table
     * @param address Updated delivery address information
     */
    @Transactional
    int update(Long id, UmsMemberReceiveAddress address);

    /**
     * Return the current user's delivery addresses
     */
    List<UmsMemberReceiveAddress> list();

    /**
     * Get address details
     * @param id Address ID
     */
    UmsMemberReceiveAddress getItem(Long id);
}
