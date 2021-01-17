package com.example.cacheservice.core;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PassiveRemoveScheduler {

    private StringCache stringCache;
    private final Long period = 1000L;

    public PassiveRemoveScheduler(StringCache stringCache) {
        this.stringCache = stringCache;
    }

    public void startJob() {
        new Thread(() -> {
            log.info("### Schedule work listening ###");
            while (true) {
                stringCache.scanForRemove();
                try {
                    Thread.sleep(period);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
