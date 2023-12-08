package com.example.ProjectExam.CleanCache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CachedService {

    @Cacheable("dataCache")
    public String getCachedData() {
        return "Cached data";
    }

    @CacheEvict(allEntries = true, value = "dataCache")
    public void clearCache() {

    }
}
