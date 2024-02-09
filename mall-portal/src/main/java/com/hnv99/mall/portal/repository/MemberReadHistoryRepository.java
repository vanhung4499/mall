package com.hnv99.mall.portal.repository;

import com.hnv99.mall.portal.domain.MemberReadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory, String> {
    /**
     * Find records by member ID with pagination and order by creation time in descending order
     */
    Page<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long memberId, Pageable pageable);

    /**
     * Delete all records by member ID
     */
    void deleteAllByMemberId(Long memberId);
}

