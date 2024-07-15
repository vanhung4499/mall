package com.union.mall.oms.model.vo;

import com.union.mall.oms.model.dto.OrderItemDTO;
import com.union.mall.ums.dto.MemberAddressDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Order confirmation response object")
@Data
public class OrderConfirmVO {

    /**
     * Order anti-repetition submission token
     */
    @Schema(description = "Order anti-repetition submission token")
    private String orderToken;

    /**
     * Order items
     */
    @Schema(description = "Order items")
    private List<OrderItemDTO> orderItems;

    /**
     * Member shipping addresses list
     */
    @Schema(description = "Member shipping addresses list")
    private List<MemberAddressDTO> addresses;

}
