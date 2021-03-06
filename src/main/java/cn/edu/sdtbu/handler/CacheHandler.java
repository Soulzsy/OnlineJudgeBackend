package cn.edu.sdtbu.handler;

import cn.edu.sdtbu.cache.CacheStore;
import cn.edu.sdtbu.model.enums.CacheStoreType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author bestsort
 * @version 1.0
 * @date 2020-04-26 16:30
 */
@Slf4j
@Component
public class CacheHandler {
    private final ConcurrentHashMap<CacheStoreType, CacheStore<String, String>> cacheMap = new ConcurrentHashMap<>();
    private static CacheStoreType STRATEGY = CacheStoreType.DEFAULT;

    /**
     * Please don't use constructor method replace {@link #init(ApplicationContext)} to init {@link #cacheMap}
     * It will cause the missing some implement. similar problems have been found in {@link CacheStoreType#DEFAULT}
     * @param context application context to fetch {@link CacheStore} implement
     */
    public void init(ApplicationContext context, CacheStoreType type) {
        for (CacheStore cacheStore : context.getBeansOfType(CacheStore.class).values()) {
            this.cacheMap.put(cacheStore.getCacheType(), cacheStore);
        }
        setStrategy(type);
    }
    public void setStrategy(CacheStoreType strategy) {
        setStrategy(strategy, strategy != STRATEGY);
    }

    private void setStrategy(CacheStoreType strategy, boolean isChanged) {
        Assert.notNull(strategy, "storage must be not null");
        if (isChanged) {
            log.info("cache middleware was changed to {}", strategy);
            STRATEGY = strategy;
        }
    }

    public String getStrategy() {
        return STRATEGY.toString();
    }

    public CacheStore<String, String> fetchCacheStore() {
        CacheStore<String, String> store = cacheMap.get(STRATEGY);
        Assert.notNull(store,"cache storage not found");
        return  store;
    }
}
