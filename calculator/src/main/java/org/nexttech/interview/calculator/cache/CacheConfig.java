package org.nexttech.interview.calculator.cache;

import org.nexttech.interview.calculator.service.CalculatorDispatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheConfig {

    @Autowired
    private CacheManager cacheManager;

    private static final Logger logger = LoggerFactory.getLogger(CalculatorDispatcherService.class);

    @Scheduled(fixedRate = 20 * 1000)
    public void clearComputationsCache() {
        logger.info("Cache has been successfully cleared");
        this.cacheManager.getCache("computations-cache").clear();
    }

}
