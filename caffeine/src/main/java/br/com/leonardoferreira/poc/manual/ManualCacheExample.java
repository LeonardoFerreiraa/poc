package br.com.leonardoferreira.poc.manual;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;

@EnableCaching
@SpringBootApplication
@EnableConfigurationProperties(CacheListProperties.class)
public class ManualCacheExample {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ManualCacheExample.class)
                .properties(
                        "caches.greeting.expire-after-access=PT10S",
                        "caches.optionalGreeting.expire-after-access=PT20S"
                )
                .run(args);
    }

}

@ConstructorBinding
@ConfigurationProperties
record CacheListProperties(Map<String, CacheProperties> caches) {
}

record CacheProperties(Duration expireAfterAccess,
                       Duration expireAfterWrite,
                       Long maximumSize,
                       Long maximumWeight) {
}

@Configuration
class CacheConfiguration {

    @Bean
    CacheManager cacheManager(final CacheListProperties cacheListProperties) {
        final SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(
                cacheListProperties.caches()
                        .entrySet()
                        .stream()
                        .map(entry -> buildCache(entry.getKey(), entry.getValue()))
                        .toList()
        );
        return manager;
    }

    private Cache buildCache(final String cacheName, final CacheProperties cacheProperties) {
        final Caffeine<Object, Object> caffeine = Caffeine.newBuilder();

        setIfNotNull(cacheProperties.expireAfterAccess(), caffeine::expireAfterAccess);
        setIfNotNull(cacheProperties.expireAfterWrite(), caffeine::expireAfterWrite);
        setIfNotNull(cacheProperties.maximumSize(), caffeine::maximumSize);
        setIfNotNull(cacheProperties.maximumWeight(), caffeine::maximumWeight);

        return new CaffeineCache(cacheName, caffeine.build());
    }

    private <T> void setIfNotNull(T value, Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

}

@Component
class MyRunner implements ApplicationRunner {

    private final MyService myService;

    MyRunner(final MyService myService) {
        this.myService = myService;
    }

    @Override
    public void run(final ApplicationArguments args) throws Exception {
        System.out.println(myService.greeting());
        System.out.println(myService.greeting());
        Thread.sleep(Duration.ofSeconds(2).toMillis());
        System.out.println(myService.greeting());

        System.out.println(myService.optionalGreeting());
        System.out.println(myService.optionalGreeting());
        System.out.println(myService.optionalGreeting());
    }

}

@Service
class MyService {

    @Cacheable(cacheNames = "greeting")
    public String greeting() {
        System.out.println("greeting");
        return "hello world";
    }

    @Cacheable(cacheNames = "optionalGreeting")
    public Optional<String> optionalGreeting() {
        System.out.println("optionalGreeting");
        return Optional.of("hello world");
    }
}
