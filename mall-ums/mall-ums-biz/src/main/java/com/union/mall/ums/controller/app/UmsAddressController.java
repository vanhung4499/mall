package com.union.mall.ums.controller.app;

import com.union.mall.common.core.result.Result;
import com.union.mall.ums.dto.MemberAddressDTO;
import com.union.mall.ums.model.entity.UmsAddress;
import com.union.mall.ums.model.form.AddressForm;
import com.union.mall.ums.service.UmsAddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "App-Member Addresses")
@RestController
@RequestMapping("/app-api/v1/addresses")
@RequiredArgsConstructor
public class UmsAddressController {

    private final UmsAddressService addressService;

    @Operation(summary= "Get current member's address list")
    @GetMapping
    public Result<List<MemberAddressDTO>> listCurrentMemberAddresses() {
        List<MemberAddressDTO> addressList = addressService.listCurrentMemberAddresses();
        return Result.success(addressList);
    }

    @Operation(summary= "Get address details")
    @GetMapping("/{addressId}")
    public Result<UmsAddress> getAddressDetail(
            @Parameter(name = "Address ID") @PathVariable Long addressId
    ) {
        UmsAddress umsAddress = addressService.getById(addressId);
        return Result.success(umsAddress);
    }

    @Operation(summary= "Add new address")
    @PostMapping
    public Result addAddress(
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.addAddress(addressForm);
        return Result.judge(result);
    }

    @Operation(summary= "Update address")
    @PutMapping("/{addressId}")
    public Result updateAddress(
            @Parameter(name = "Address ID") @PathVariable Long addressId,
            @RequestBody @Validated AddressForm addressForm
    ) {
        boolean result = addressService.updateAddress(addressForm);
        return Result.judge(result);
    }

    @Operation(summary= "Delete address")
    @DeleteMapping("/{ids}")
    public Result deleteAddress(
            @Parameter(name = "Address IDs, separated by comma (,)") @PathVariable String ids
    ) {
        boolean status = addressService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.judge(status);
    }

}
