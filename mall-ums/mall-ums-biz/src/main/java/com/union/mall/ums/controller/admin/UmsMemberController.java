package com.union.mall.ums.controller.admin;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.core.constant.GlobalConstants;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.ums.model.entity.UmsMember;
import com.union.mall.ums.service.UmsMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin - Member Management")
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class UmsMemberController {

    private final UmsMemberService memberService;

    @Operation(summary= "Member Page List")
    @GetMapping
    public PageResult<UmsMember> getMemberPage(
            @Parameter(name = "Page Number") Long pageNum,
            @Parameter(name = "Page Size") Long pageSize,
            @Parameter(name = "Member Nickname") String nickName
    ) {
        IPage<UmsMember> result = memberService.list(new Page<>(pageNum, pageSize), nickName);
        return PageResult.success(result);
    }

    @Operation(summary= "Update Member")
    @PutMapping(value = "/{memberId}")
    public <T> Result<T> update(
            @Parameter(name = "Member ID") @PathVariable Long memberId,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.updateById(member);
        return Result.judge(status);
    }

    @Operation(summary= "Update Member Status")
    @PatchMapping("/{memberId}/status")
    public <T> Result<T> updateMemberStatus(
            @Parameter(name = "Member ID") @PathVariable Long memberId,
            @RequestBody UmsMember member
    ) {
        boolean status = memberService.update(
                new LambdaUpdateWrapper<UmsMember>()
                        .eq(UmsMember::getId, memberId)
                        .set(UmsMember::getStatus, member.getStatus())
        );
        return Result.judge(status);
    }

    @Operation(summary= "Delete Member")
    @DeleteMapping("/{ids}")
    public <T> Result<T> delete(
            @Parameter(name = "Member ID(s), multiple IDs joined by a comma (,)") @PathVariable String ids
    ) {
        boolean status = memberService.update(new LambdaUpdateWrapper<UmsMember>()
                .in(UmsMember::getId, Arrays.asList(ids.split(",")))
                .set(UmsMember::getDeleted, GlobalConstants.STATUS_YES));
        return Result.judge(status);
    }
}
