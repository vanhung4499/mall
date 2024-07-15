package com.union.mall.ums.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.constant.MemberConstants;
import com.union.mall.common.core.result.ResultCode;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.common.web.exception.BizException;
import com.union.mall.pms.model.vo.ProductHistoryVO;
import com.union.mall.ums.converter.AddressConvert;
import com.union.mall.ums.converter.MemberConvert;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.dto.MemberAuthDTO;
import com.union.mall.ums.dto.MemberRegisterDTO;
import com.union.mall.ums.mapper.UmsMemberMapper;
import com.union.mall.ums.model.entity.UmsAddress;
import com.union.mall.ums.model.entity.UmsMember;
import com.union.mall.ums.model.vo.MemberVO;
import com.union.mall.ums.service.UmsAddressService;
import com.union.mall.ums.service.UmsMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {

    private final RedisTemplate redisTemplate;
    private final MemberConvert memberConvert;
    private final AddressConvert addressConvert;
    private final UmsAddressService addressService;

    @Override
    public IPage<UmsMember> list(Page<UmsMember> page, String nickname) {
        List<UmsMember> list = this.baseMapper.list(page, nickname);
        page.setRecords(list);
        return page;
    }

    @Override
    public void addProductViewHistory(ProductHistoryVO product, Long userId) {
        if (userId != null) {
            String key = MemberConstants.USER_PRODUCT_HISTORY + userId;
            redisTemplate.opsForZSet().add(key, product, System.currentTimeMillis());
            Long size = redisTemplate.opsForZSet().size(key);
            if (size > 10) {
                redisTemplate.opsForZSet().removeRange(key, 0, size - 11);
            }
        }
    }

    @Override
    public Set<ProductHistoryVO> getProductViewHistory(Long userId) {
        return redisTemplate.opsForZSet().reverseRange(MemberConstants.USER_PRODUCT_HISTORY + userId, 0, 9);
    }

    /**
     * Get member authentication information by openid
     *
     * @param openid WeChat unique identifier
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByOpenid(String openid) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getOpenid, openid)
                .select(UmsMember::getId,
                        UmsMember::getOpenid,
                        UmsMember::getStatus
                )
        );

        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2OpenidAuthDTO(entity);
    }

    /**
     * Get member authentication information by mobile number
     *
     * @param mobile
     * @return
     */
    @Override
    public MemberAuthDTO getMemberByMobile(String mobile) {
        UmsMember entity = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getMobile, mobile)
                .select(UmsMember::getId,
                        UmsMember::getMobile,
                        UmsMember::getStatus
                )
        );

        if (entity == null) {
            throw new BizException(ResultCode.USER_NOT_EXIST);
        }
        return memberConvert.entity2MobileAuthDTO(entity);
    }

    /**
     * Add a new member
     *
     * @param memberRegisterDTO
     * @return
     */
    @Override
    public Long addMember(MemberRegisterDTO memberRegisterDTO) {
        UmsMember umsMember = memberConvert.dto2Entity(memberRegisterDTO);
        boolean result = this.save(umsMember);
        Assert.isTrue(result, "Failed to add new member");
        return umsMember.getId();
    }

    /**
     * Get current logged-in member information
     *
     * @return
     */
    @Override
    public MemberVO getCurrMemberInfo() {
        Long memberId = SecurityUtils.getMemberId();
        UmsMember umsMember = this.getOne(new LambdaQueryWrapper<UmsMember>()
                .eq(UmsMember::getId, memberId)
                .select(UmsMember::getId,
                        UmsMember::getNickName,
                        UmsMember::getAvatarUrl,
                        UmsMember::getMobile,
                        UmsMember::getBalance
                )
        );
        MemberVO memberVO = new MemberVO();
        BeanUtil.copyProperties(umsMember, memberVO);
        return memberVO;
    }

    /**
     * Get member addresses
     *
     * @param memberId
     * @return
     */
    @Override
    public List<MemberAddressDTO> listMemberAddress(Long memberId) {

        List<UmsAddress> entities = addressService.list(
                new LambdaQueryWrapper<UmsAddress>()
                        .eq(UmsAddress::getMemberId, memberId)
        );

        List<MemberAddressDTO> list = addressConvert.entity2Dto(entities);
        return list;
    }
}
