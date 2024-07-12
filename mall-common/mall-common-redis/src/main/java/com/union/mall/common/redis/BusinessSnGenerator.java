package com.union.mall.common.redis;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class BusinessSnGenerator {

    private final RedisTemplate redisTemplate;

    /**
     * Generates a serial number based on business type and specified digits.
     *
     * @param businessType The type of business for which the serial number is generated.
     * @param digit        The number of digits in the serial number.
     * @return The generated serial number.
     */
    public String generateSerialNo(String businessType, Integer digit) {
        if (StrUtil.isBlank(businessType)) {
            businessType = "COMMON";
        }
        String date = LocalDateTime.now(ZoneOffset.of("+7"))
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String key = "SN:" + businessType + ":" + date;
        Long increment = redisTemplate.opsForValue().increment(key);
        return date + String.format("%0" + digit + "d", increment);
    }

    /**
     * Generates a serial number with default digits (6).
     *
     * @return The generated serial number.
     */
    public String generateSerialNo() {
        return this.generateSerialNo(null, 6);
    }

    /**
     * Generates a serial number based on business type with default digits (6).
     *
     * @param businessType The type of business for which the serial number is generated.
     * @return The generated serial number.
     */
    public String generateSerialNo(String businessType) {
        return this.generateSerialNo(businessType, 6);
    }

}
