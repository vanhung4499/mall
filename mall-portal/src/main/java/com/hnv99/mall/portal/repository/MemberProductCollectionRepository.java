package com.hnv99.mall.portal.repository;

import com.hnv99.mall.portal.domain.MemberProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberProductCollectionRepository extends MongoRepository<MemberProductCollection, String> {
    /**
     * Find a record by member ID and product ID
     */
    MemberProductCollection findByMemberIdAndProductId(Long memberId, Long productId);

    /**
     * Delete a record by member ID and product ID
     */
    int deleteByMemberIdAndProductId(Long memberId, Long productId);

    /**
     * Find records by member ID with pagination
     */
    Page<MemberProductCollection> findByMemberId(Long memberId, Pageable pageable);

    /**
     * Delete all records by member ID
     */
    void deleteAllByMemberId(Long memberId);
}
