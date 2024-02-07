package com.hnv99.mall.mapper;

import com.hnv99.mall.model.PmsCommentReply;
import com.hnv99.mall.model.PmsCommentReplyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PmsCommentReplyMapper {
    long countByExample(PmsCommentReplyExample example);

    int deleteByExample(PmsCommentReplyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PmsCommentReply row);

    int insertSelective(PmsCommentReply row);

    List<PmsCommentReply> selectByExample(PmsCommentReplyExample example);

    PmsCommentReply selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") PmsCommentReply row, @Param("example") PmsCommentReplyExample example);

    int updateByExample(@Param("row") PmsCommentReply row, @Param("example") PmsCommentReplyExample example);

    int updateByPrimaryKeySelective(PmsCommentReply row);

    int updateByPrimaryKey(PmsCommentReply row);
}