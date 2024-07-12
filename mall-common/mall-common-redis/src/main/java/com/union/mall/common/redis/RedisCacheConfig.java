package com.union.mall.common.redis;

import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class RedisCacheConfig {

    /**
     * Configures the Redis cache manager.
     *
     * @param redisConnectionFactory The Redis connection factory.
     * @param cacheProperties        The cache properties to configure Redis caching.
     * @return The configured Redis cache manager.
     */
    @Bean
    public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory, CacheProperties cacheProperties){
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration(cacheProperties))
                .build();
    }

    /**
     * Configures the default Redis cache configuration.
     *
     * @param cacheProperties The cache properties to customize Redis cache configuration.
     * @return The configured Redis cache configuration.
     */
    @Bean
    RedisCacheConfiguration redisCacheConfiguration(CacheProperties cacheProperties) {

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();

        // Serialize cache keys as strings
        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.string()));

        // Serialize cache values as JSON
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.json()));

        CacheProperties.Redis redisProperties = cacheProperties.getRedis();

        // Set entry time-to-live if specified
        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }

        // Disable caching of null values if configured
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }

        // Disable use of key prefix if configured
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        // Customize the key prefix format
        config = config.computePrefixWith(name -> name + ":"); // Override default key double colon prefix

        return config;
    }
}
