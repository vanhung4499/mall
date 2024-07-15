package com.union.mall.ums.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.union.mall.pms.model.vo.ProductHistoryVO;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import com.union.mall.ums.model.entity.UmsMember;
import com.union.mall.ums.model.vo.MemberVO;


import java.util.List;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface UmsMemberService extends IService<UmsMember> {

    IPage<UmsMember> list(Page<UmsMember> page, String nickname);

    void addProductViewHistory(ProductHistoryVO product, Long userId);

    Set<ProductHistoryVO> getProductViewHistory(Long userId);

    /**
     * Get member authentication information based on openid
     *
     * @param openid
     * @return
     */
    MemberAuthDTO getMemberByOpenid(String openid);

    /**
     * Get member authentication information based on mobile number
     *
     * @param mobile
     * @return
     */
    MemberAuthDTO getMemberByMobile(String mobile);

    /**
     * Add a new member
     *
     * @param member
     * @return
     */
    Long addMember(MemberRegisterDTO member);

    /**
     * Get information of the currently logged-in member
     *
     * @return
     */
    MemberVO getCurrMemberInfo();

    /**
     * Get a list of member addresses
     *
     * @param memberId
     * @return
     */
    List<MemberAddressDTO> listMemberAddress(Long memberId);
}
