package com.example.cacheservice.web.webConfig;

import com.example.cacheservice.core.PassiveRemoveScheduler;
import com.example.cacheservice.core.StringCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private StringCache stringCache;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        PassiveRemoveScheduler scheduler = new PassiveRemoveScheduler(stringCache);
        scheduler.startJob();
    }
}
