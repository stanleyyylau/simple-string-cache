package com.example.cacheservice.core;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CacheEntity<T> {
    private String key;
    private T value;
    private Date expireDate;
    private Integer count;

    public CacheEntity(String key, T value, Date expireDate) {
        this.key = key;
        this.value = value;
        this.expireDate = expireDate;
        this.count = 0;
    }

    public CacheEntity(String key, T value) {
        this.key = key;
        this.value = value;
        this.expireDate = CommonUtil.getDateByExpire(10 * 1000L);
        this.count = 0;
    }
}
