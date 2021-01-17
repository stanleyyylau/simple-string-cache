package com.example.cacheservice.web.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CacheDTO {
    private String key;
    private String value;
    private Long expireInMs;
}
