package com.example.cacheservice.core;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class StringCache {

    public static final Long DEFAULT_EXPIRE = 10 * 1000L;  // 10 seconds by default
    private final Float memoryLimit = 0.8f;
    private final Float removePercent = 0.2f;
    private static Map<String, CacheEntity<String>> cache = new ConcurrentHashMap<>();
    private static SortedLinkListByCount sortedLinkList = new SortedLinkListByCount();
    private static SortedLinkListByExpire sortedLinkListByExpire = new SortedLinkListByExpire();

    public void removeByKey(String key) {
        sortedLinkList.removeByKey(key);
        sortedLinkListByExpire.removeByKey(key);
        cache.remove(key);
    }

    public void flushAll() {
        StringCache.sortedLinkList = new SortedLinkListByCount();
        StringCache.sortedLinkListByExpire = new SortedLinkListByExpire();
        cache.clear();
    }

    public boolean containsString(String key) {
        return cache.containsKey(key);
    }

    public Optional<String> get(String key) {
        // Key missing
        CacheEntity<String> cacheEntity = cache.get(key);
        if (cacheEntity == null) {
            return Optional.empty();
        }

        // Passive expire check
        if (this.isKeyExpire(key)) {
            this.removeByKey(key);
            return Optional.empty();
        }

        // Update visit count and return
        sortedLinkList.updateByKey(key, cacheEntity.getCount() + 1);
        return Optional.of(cacheEntity.getValue());
    }

    // update
    public void put(String key, CacheEntity<String> entity) {
        cache.replace(key, entity);
    }

    public void put(String key, String value) {
        this.put(key, value, DEFAULT_EXPIRE);
    }

    public void put(String key, String value, Long expire) {
        // duplicate key, run update
        if(containsString(key)) {
            CacheEntity cacheEntity = cache.get(key);
            cacheEntity.setValue(value);
            // update do not regard as visit
            // update expire is forbidden
            this.put(key, cacheEntity);
            return;
        }

        // run LRU if needed
        if (isOutOfMemoryLimit()) {
            this.freeMemory();
        }

        // save cache
        CacheEntity<String> cacheEntity = new CacheEntity<>(key, value, CommonUtil.getDateByExpire(expire));
        sortedLinkList.addToHead(cacheEntity);
        sortedLinkListByExpire.add(cacheEntity);
        cache.put(key, cacheEntity);
    }

    private Boolean isOutOfMemoryLimit() {
        return Runtime.getRuntime().freeMemory() / Runtime.getRuntime().totalMemory() > memoryLimit;
    }

    private void freeMemory() {
        Integer removeCount = 0;
        Integer totalCount = cache.size();
        while (removeCount / totalCount > removePercent) {
            String keyToRemove = sortedLinkList.getHeadKey();
            this.removeByKey(keyToRemove);
            log.info("free up memory for key: " + keyToRemove);
            removeCount++;
        }
    }

    private Boolean isKeyExpire(String key) {
        return CommonUtil.isDateExpire(cache.get(key).getExpireDate());
    }

    // check the linked list instead of full scan for better performance
    public void scanForRemove() {
        List<String> list = sortedLinkListByExpire.collectExpireKeys();
        for(String key : list) {
            this.removeByKey(key);
            log.info("[SCAN] Deleted key: " + key);
        }
    }

}
