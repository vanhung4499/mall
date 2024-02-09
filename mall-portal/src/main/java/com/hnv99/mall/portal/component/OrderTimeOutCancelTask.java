package com.hnv99.mall.portal.component;

import com.hnv99.mall.portal.service.OmsPortalOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderTimeOutCancelTask {
    // Logger for logging
    private final Logger LOGGER = LoggerFactory.getLogger(OrderTimeOutCancelTask.class);

    // Autowired OmsPortalOrderService to cancel timeout orders
    @Autowired
    private OmsPortalOrderService portalOrderService;

    /**
     * Cron expression: Seconds Minutes Hours DayofMonth Month DayofWeek [Year]
     * Scans every 10 minutes, scans for timeout unpaid orders, and performs cancellation operations
     */
    @Scheduled(cron = "0 0/10 * ? * ?")
    private void cancelTimeOutOrder(){
        // Cancel timeout orders and release the locked stock according to the SKU number
        Integer count = portalOrderService.cancelTimeOutOrder();
        // Log the number of canceled orders
        LOGGER.info("Cancelled orders and released the locked stock according to the SKU number, number of cancelled orders: {}", count);
    }
}


