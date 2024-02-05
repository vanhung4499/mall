package com.hnv99.mall.common.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RedisService {

    /**
     * Save property
     */
    void set(String key, Object value, long time);

    /**
     * Save property
     */
    void set(String key, Object value);

    /**
     * Get property
     */
    Object get(String key);

    /**
     * Delete property
     */
    Boolean del(String key);

    /**
     * Delete properties in bulk
     */
    Long del(List<String> keys);

    /**
     * Set expiration time
     */
    Boolean expire(String key, long time);

    /**
     * Get expiration time
     */
    Long getExpire(String key);

    /**
     * Check if the property exists
     */
    Boolean hasKey(String key);

    /**
     * Increment by delta
     */
    Long incr(String key, long delta);

    /**
     * Decrement by delta
     */
    Long decr(String key, long delta);

    /**
     * Get property in Hash structure
     */
    Object hGet(String key, String hashKey);

    /**
     * Put a property into Hash structure
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * Put a property into Hash structure
     */
    void hSet(String key, String hashKey, Object value);

    /**
     * Directly get all properties in Hash structure
     */
    Map<Object, Object> hGetAll(String key);

    /**
     * Directly set all properties in Hash structure
     */
    Boolean hSetAll(String key, Map<String, Object> map, long time);

    /**
     * Directly set all properties in Hash structure
     */
    void hSetAll(String key, Map<String, ?> map);

    /**
     * Delete property in Hash structure
     */
    void hDel(String key, Object... hashKey);

    /**
     * Check if Hash structure has the property
     */
    Boolean hHasKey(String key, String hashKey);

    /**
     * Increment property in Hash structure
     */
    Long hIncr(String key, String hashKey, Long delta);

    /**
     * Decrement property in Hash structure
     */
    Long hDecr(String key, String hashKey, Long delta);

    /**
     * Get Set structure
     */
    Set<Object> sMembers(String key);

    /**
     * Add property to Set structure
     */
    Long sAdd(String key, Object... values);

    /**
     * Add property to Set structure
     */
    Long sAdd(String key, long time, Object... values);

    /**
     * Check if property is in Set
     */
    Boolean sIsMember(String key, Object value);

    /**
     * Get size of Set structure
     */
    Long sSize(String key);

    /**
     * Remove property from Set structure
     */
    Long sRemove(String key, Object... values);

    /**
     * Get property in List structure
     */
    List<Object> lRange(String key, long start, long end);

    /**
     * Get size of List structure
     */
    Long lSize(String key);

    /**
     * Get property by index in List
     */
    Object lIndex(String key, long index);

    /**
     * Add property to List structure
     */
    Long lPush(String key, Object value);

    /**
     * Add property to List structure
     */
    Long lPush(String key, Object value, long time);

    /**
     * Add properties in bulk to List structure
     */
    Long lPushAll(String key, Object... values);

    /**
     * Add properties in bulk to List structure
     */
    Long lPushAll(String key, Long time, Object... values);

    /**
     * Remove property from List structure
     */
    Long lRemove(String key, long count, Object value);
}
