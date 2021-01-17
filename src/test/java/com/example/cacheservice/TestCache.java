package com.example.cacheservice;
import com.example.cacheservice.core.CacheEntity;
import com.example.cacheservice.core.CommonUtil;
import com.example.cacheservice.core.PassiveRemoveScheduler;
import com.example.cacheservice.core.StringCache;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class TestCache {
    @Test
    public void testActiveRemove() {
        StringCache cache = new StringCache();
        PassiveRemoveScheduler scheduler = new PassiveRemoveScheduler(cache);
        scheduler.startJob();
        cache.put("key1", "value1", 2 * 1000L);
        cache.put("key2", "value2", 5 * 1000L);
        cache.put("key3", "value3", 8 * 1000L);

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("key1:" + cache.get("key1"));
        log.info("key1:" + cache.get("key2"));
        log.info("key3:" + cache.get("key3"));
    }

    @Test
    public void testPassiveRemove() {
        StringCache cache = new StringCache();
        cache.put("key1", "value1", 2 * 1000L);
        cache.put("key2", "value2", 5 * 1000L);
        cache.put("key3", "value3", 8 * 1000L);

        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("key1:" + cache.get("key1"));
        log.info("key1:" + cache.get("key2"));
        log.info("key3:" + cache.get("key3"));
    }

    @Test
    public void testMemoryLimit() {
        StringCache cache = new StringCache();
        List<CacheEntity> list1 = new ArrayList<>();
        String bigString = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis faucibus sit amet magna mollis dignissim. Maecenas ullamcorper neque lacus. Proin id leo vel enim porttitor pellentesque eget a est. Praesent suscipit placerat ullamcorper. Fusce vitae orci ac urna convallis sodales. Vestibulum eu turpis nec odio venenatis fringilla sed id diam. Aliquam at ex augue. Suspendisse non est eu orci ultrices fringilla. Morbi venenatis auctor placerat. Nunc aliquet tortor vel vulputate convallis. Etiam eu nisl nunc. Duis risus nisi, sollicitudin ac vehicula eget, condimentum a lectus. Nam quis urna eget massa varius vestibulum. Cras dapibus lorem hendrerit cursus molestie. Nam finibus mi nibh, nec ultrices ipsum porttitor sed. Mauris quis est congue eros cursus bibendum.\n" +
                "\n" +
                "Sed sit amet orci erat. Vivamus ac libero venenatis, dignissim orci nec, tincidunt velit. Etiam interdum tellus varius dui volutpat pellentesque. Ut ipsum neque, vehicula vel elementum vitae, mattis eget ligula. Mauris lorem arcu, aliquet ut cursus ut est.\n" +
                "\n";
        for (int i = 0; i < 1000000; i++) {
            log.info("putting key: " + i);
            int mb = 1024 * 1024;
            Long freeMem = Runtime.getRuntime().freeMemory() / mb;
            Long total = Runtime.getRuntime().maxMemory();
            Long rate = freeMem / total;
            log.info("Free: " + String.valueOf(freeMem) + "total: ", String.valueOf(total) + "diff: " + String.valueOf(total / freeMem));

            CacheEntity cacheEntity = new CacheEntity(String.valueOf(i), bigString, CommonUtil.getDateByExpire(999 * 1000L));
            cache.put(String.valueOf(i), bigString + cacheEntity.toString(), 2000 * 1000L);
        }
    }

}
