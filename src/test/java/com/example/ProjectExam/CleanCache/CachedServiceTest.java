package com.example.ProjectExam.CleanCache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.support.NoOpCache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class CachedServiceTest {

    @Mock
    private CacheManager cacheManager;

    private CacheableClass cacheableClass;

    @BeforeEach
    public void setup() {
        cacheableClass = new CacheableClass(cacheManager);
    }

    @Test
    public void testGetCachedData() {
        String expectedData = "Cached data";


        String actualData = cacheableClass.getCachedData();


        assertEquals(expectedData, actualData);
    }

    @Test
    public void testGetCachedDataError() {
        String expectedData = "Cached data1";


        String actualData = cacheableClass.getCachedData();


        assertNotEquals(expectedData, actualData);
    }
    @Test
    public void testClearCache() {
        cacheableClass.clearCache();
        cacheableClass.getCachedData().isEmpty();

    }
    private static class CacheableClass {

        private final CacheManager cacheManager;

        public CacheableClass(CacheManager cacheManager) {
            this.cacheManager = cacheManager;
        }

        @Cacheable("dataCache")
        public String getCachedData() {
            return "Cached data";
        }

        @CacheEvict(allEntries = true, value = "dataCache")
        public void clearCache() {
            Cache mockCach=new NoOpCache("polop");
            Cache dataCache = cacheManager.getCache("dataCache");
           dataCache=mockCach;
            dataCache.evict(true);
        }
    }

}