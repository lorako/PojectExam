package com.example.ProjectExam.CleanCache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheCleanScheduler {



        @Autowired
        private CachedService cachedService;

        @Scheduled(cron = "0 0 0 * * ?")
        public void clearCache() {
            cachedService.clearCache();
        }

}
