package com.union.mall.oms.config;

import com.union.mall.common.core.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Configuration
@Slf4j
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        int cpuCoreSize = Runtime.getRuntime().availableProcessors();
        log.info("Current CPU core count: {}", cpuCoreSize);

        /**
         * CPU-bound: Core pool size = CPU cores + 1   √
         * I/O-bound: Core pool size = 2 * CPU cores + 1
         */
        Integer corePoolSize = cpuCoreSize + 1;

        return new ThreadPoolExecutor(
                corePoolSize,                        // Core pool size
                2 * corePoolSize,                    // Maximum pool size
                30,                                 // Keep-alive time for excess threads
                TimeUnit.SECONDS,                    // Time unit for keep-alive time
                new ArrayBlockingQueue<>(1000),      // Blocking queue for tasks
                new NamedThreadFactory("order")      // Thread factory for creating threads
        );
    }

}
