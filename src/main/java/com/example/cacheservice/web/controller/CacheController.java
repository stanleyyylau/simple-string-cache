package com.example.cacheservice.web.controller;

import com.example.cacheservice.web.pojo.CacheDTO;
import com.example.cacheservice.web.pojo.CacheVO;
import com.example.cacheservice.web.service.ICacheService;
import com.example.cacheservice.web.webConfig.NotFoundException;
import com.example.cacheservice.web.webConfig.UnifyResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
@Api
public class CacheController {

    @Autowired
    private ICacheService cacheService;

    @GetMapping("/{key}")
    public CacheVO getCache(@PathVariable String key) {
        String value = cacheService.get(key);
        return new CacheVO(value);
    }

    @PostMapping("/")
    public void putCache(@RequestBody @Validated CacheDTO cacheDTO) {
        if (StringUtils.isEmpty(cacheDTO.getKey()) || StringUtils.isEmpty(cacheDTO.getValue())) {
            throw new NotFoundException(4444);
        }
        if (cacheDTO.getExpireInMs() == null) {
            cacheService.put(cacheDTO.getKey(), cacheDTO.getValue());
        } else {
            cacheService.put(cacheDTO.getKey(), cacheDTO.getValue(), cacheDTO.getExpireInMs());
        }
        UnifyResponse.createSuccess(8888);
    }
}
