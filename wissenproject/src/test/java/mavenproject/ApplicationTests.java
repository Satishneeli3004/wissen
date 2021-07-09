package mavenproject;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Collection;

@Slf4j
@SpringBootTest
class ApplicationTests {

    @Autowired
    private CacheManager cacheManager;

    @Test
    void contextLoads() {
        Collection<String> cacheNames = cacheManager.getCacheNames();
        log.debug("{}", cacheNames);
    }

}
