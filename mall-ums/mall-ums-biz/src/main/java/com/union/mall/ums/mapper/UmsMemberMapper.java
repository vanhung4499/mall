package com.union.mall.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.ums.model.entity.UmsMember;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface UmsMemberMapper extends BaseMapper<UmsMember> {

    @Select("<script>" +
            " SELECT * from ums_member " +
            " <if test ='nickname !=null and nickname.trim() neq \"\" ' >" +
            "       WHERE nick_name like concat('%',#{nickname},'%')" +
            " </if>" +
            " ORDER BY update_time DESC, create_time DESC" +
            "</script>")
    @Results({
            @Result(id = true, column = "id", property = "id"),
            @Result(property = "addressList", column = "id", many = @Many(select = "com.youlai.mall.ums.mapper.UmsAddressMapper.listByUserId"))
    })
    List<UmsMember> list(Page<UmsMember> page, String nickname);


}
