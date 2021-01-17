package com.example.cacheservice.web.service;

public interface ICacheService {

    String get(String key);
    void put(String key, String value);
    void put(String key, String value, Long expireInMs);

}
