package com.union.mall.common.web.aspect;

import cn.hutool.core.util.StrUtil;
import com.union.mall.common.core.result.ResultCode;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.common.web.exception.BizException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.TimeUnit;


/**
 * Duplicate Submission Prevention Aspect
 *
 * @author vanhung4499
 */
@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class DuplicateSubmitAspect {

    private final RedissonClient redissonClient;

    private static final String RESUBMIT_LOCK_PREFIX = "LOCK:RESUBMIT:";

    /**
     * Pointcut for preventing duplicate submissions.
     */
    @Pointcut("@annotation(preventDuplicateResubmit)")
    public void preventDuplicateSubmitPointCut(PreventDuplicateResubmit preventDuplicateResubmit) {
        log.info("Defining pointcut for preventing duplicate submissions");
    }

    @Around("preventDuplicateSubmitPointCut(preventDuplicateResubmit)")
    public Object doAround(ProceedingJoinPoint pjp, PreventDuplicateResubmit preventDuplicateResubmit) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String jti = SecurityUtils.getJti();
        if (StrUtil.isNotBlank(jti)) {
            String resubmitLockKey = RESUBMIT_LOCK_PREFIX + jti + ":" + request.getMethod() + "-" + request.getRequestURI();
            int expire = preventDuplicateResubmit.expire(); // Expiration time for preventing duplicate submissions
            RLock lock = redissonClient.getLock(resubmitLockKey);
            boolean lockResult = lock.tryLock(0, expire, TimeUnit.SECONDS); // If lock acquisition fails, return false immediately
            if (!lockResult) {
                throw new BizException(ResultCode.REPEAT_SUBMIT_ERROR); // Throw an exception indicating duplicate submission
            }
        }
        return pjp.proceed();
    }
}
