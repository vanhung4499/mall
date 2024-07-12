package com.union.mall.common.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Configuration
public class RedisConfig {

    /**
     * Customizes RedisTemplate for serialization.
     *
     * @param redisConnectionFactory The Redis connection factory.
     * @return The configured RedisTemplate instance.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // Configure Redis key and value serializers
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // Configure Redis hash key and hash value serializers
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(RedisSerializer.json());

        redisTemplate.afterPropertiesSet(); // Initialize the RedisTemplate

        return redisTemplate;
    }

}
