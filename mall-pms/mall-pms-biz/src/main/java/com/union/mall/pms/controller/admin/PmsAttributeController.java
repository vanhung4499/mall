package com.union.mall.pms.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.union.mall.common.core.result.Result;
import com.union.mall.pms.model.entity.PmsCategoryAttribute;
import com.union.mall.pms.model.form.PmsCategoryAttributeForm;
import com.union.mall.pms.service.AttributeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "Admin-Product Attributes")
@RestController
@RequestMapping("/api/v1/attributes")
@Slf4j
@AllArgsConstructor
public class PmsAttributeController {

    private AttributeService attributeService;

    @Operation(summary= "Attribute List")
    @GetMapping
    public Result getAttributeList(
            @Parameter(name = "Product Category ID") Long categoryId,
            @Parameter(name = "Type (1: Specification; 2: Attribute)") Integer type
    ) {
        List<PmsCategoryAttribute> list = attributeService.list(new LambdaQueryWrapper<PmsCategoryAttribute>()
                .eq(categoryId != null, PmsCategoryAttribute::getCategoryId, categoryId)
                .eq(type != null, PmsCategoryAttribute::getType, type)
        );
        return Result.success(list);
    }

    @Operation(summary= "Batch Add/Update")
    @PostMapping("/batch")
    public Result saveBatch(@RequestBody PmsCategoryAttributeForm pmsCategoryAttributeForm) {
        boolean result = attributeService.saveBatch(pmsCategoryAttributeForm);
        return Result.judge(result);
    }
}
