package com.union.mall.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.model.entity.UmsAddress;
import com.union.mall.ums.model.form.AddressForm;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface UmsAddressService extends IService<UmsAddress> {

    /**
     * Add a new address
     *
     * @param addressForm
     * @return
     */
    boolean addAddress(AddressForm addressForm);

    /**
     * Update an existing address
     *
     * @param addressForm
     * @return
     */
    boolean updateAddress(AddressForm addressForm);

    /**
     * Get the list of addresses for the currently logged-in member
     *
     * @return
     */
    List<MemberAddressDTO> listCurrentMemberAddresses();
}
