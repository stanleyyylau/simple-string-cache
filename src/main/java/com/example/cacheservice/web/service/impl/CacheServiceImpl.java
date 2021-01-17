package com.example.cacheservice.web.service.impl;

import com.example.cacheservice.core.StringCache;
import com.example.cacheservice.web.service.ICacheService;
import com.example.cacheservice.web.webConfig.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CacheServiceImpl implements ICacheService {

    @Autowired
    private StringCache stringCache;

    @Override
    public String get(String key) {
        Optional<String> s = stringCache.get(key);
        s.orElseThrow(() -> new NotFoundException(888));
        return s.get();
    }

    @Override
    public void put(String key, String value) {
        stringCache.put(key, value);
    }

    @Override
    public void put(String key, String value, Long expireInMs) {
        stringCache.put(key, value, expireInMs);
    }
}
