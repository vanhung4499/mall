package com.hnv99.mall.portal.repository;

import com.hnv99.mall.portal.domain.MemberBrandAttention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberBrandAttentionRepository extends MongoRepository<MemberBrandAttention, String> {
    /**
     * Find a record by member ID and brand ID
     */
    MemberBrandAttention findByMemberIdAndBrandId(Long memberId, Long brandId);

    /**
     * Delete a record by member ID and brand ID
     */
    int deleteByMemberIdAndBrandId(Long memberId, Long brandId);

    /**
     * Find records by member ID with pagination
     */
    Page<MemberBrandAttention> findByMemberId(Long memberId, Pageable pageable);

    /**
     * Delete all records by member ID
     */
    void deleteAllByMemberId(Long memberId);
}
