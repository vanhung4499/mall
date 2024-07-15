package com.union.mall.ums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.GlobalConstants;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.mapper.UmsAddressMapper;
import com.union.mall.ums.model.entity.UmsAddress;
import com.union.mall.ums.model.form.AddressForm;
import com.union.mall.ums.service.UmsAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
public class UmsAddressServiceImpl extends ServiceImpl<UmsAddressMapper, UmsAddress> implements UmsAddressService {

    /**
     * Add a new address
     *
     * @param addressForm
     * @return
     */
    @Override
    @Transactional
    public boolean addAddress(AddressForm addressForm) {
        Long memberId = SecurityUtils.getMemberId();

        UmsAddress umsAddress = new UmsAddress();
        BeanUtil.copyProperties(addressForm, umsAddress);
        umsAddress.setMemberId(memberId);
        boolean result = this.save(umsAddress);
        if (result) {
            // Update other default addresses to non-default
            if (GlobalConstants.STATUS_YES.equals(addressForm.getDefaulted())) {
                this.update(new LambdaUpdateWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, memberId)
                        .eq(UmsAddress::getDefaulted, 1)
                        .ne(UmsAddress::getId, umsAddress.getId())
                        .set(UmsAddress::getDefaulted, 0)
                );
            }
        }
        return result;
    }

    /**
     * Update an existing address
     *
     * @param addressForm
     * @return
     */
    @Override
    public boolean updateAddress(AddressForm addressForm) {
        Long memberId = SecurityUtils.getMemberId();

        UmsAddress umsAddress = new UmsAddress();
        BeanUtil.copyProperties(addressForm, umsAddress);

        boolean result = this.updateById(umsAddress);

        if (result) {
            // Update other default addresses to non-default
            if (GlobalConstants.STATUS_YES.equals(addressForm.getDefaulted())) {
                this.update(new LambdaUpdateWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, memberId)
                        .eq(UmsAddress::getDefaulted, 1)
                        .ne(UmsAddress::getId, umsAddress.getId())
                        .set(UmsAddress::getDefaulted, 0)
                );
            }
        }
        return result;
    }

    /**
     * Get the list of addresses for the currently logged-in member
     *
     * @return
     */
    @Override
    public List<MemberAddressDTO> listCurrentMemberAddresses() {
        Long memberId = SecurityUtils.getMemberId();
        List<UmsAddress> umsAddressList = this.list(new LambdaQueryWrapper<UmsAddress>()
                .eq(UmsAddress::getMemberId, memberId)
                .orderByDesc(UmsAddress::getDefaulted) // Default address at the top
        );
        List<MemberAddressDTO> memberAddressList = Optional.ofNullable(umsAddressList).orElse(new ArrayList<>()).stream()
                .map(umsAddress -> {
                    MemberAddressDTO memberAddressDTO = new MemberAddressDTO();
                    BeanUtil.copyProperties(umsAddress, memberAddressDTO);
                    return memberAddressDTO;
                }).collect(Collectors.toList());
        return memberAddressList;
    }
}
