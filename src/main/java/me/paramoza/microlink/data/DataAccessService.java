package me.paramoza.microlink.data;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.paramoza.microlink.entity.UrlRedirect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.Duration;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DataAccessService {
    private final UrlRepository urlRepository;

    private final RedisTemplate<String, UrlRedirect> urlRedirectRedisTemplate;

    private static final String REDIS_KEY_PREFIX = "url_redirect_";
    private static final int REDIS_TTL_SECONDS = 60; // 1 minute TTL for cached redirects

    public void setUrlRedirect(UrlRedirect urlRedirect) {
        urlRedirectRedisTemplate.opsForValue().set(
                REDIS_KEY_PREFIX + urlRedirect.getShortened(),
                urlRedirect,
                Duration.ofSeconds(REDIS_TTL_SECONDS)
        );
    }

    public UrlRedirect getUrlRedirect(String shortened) throws FileNotFoundException {
        UrlRedirect foundInRedis = urlRedirectRedisTemplate.opsForValue().get(REDIS_KEY_PREFIX + shortened);

        if (foundInRedis != null) {
            log.info("Cache hit: {}", foundInRedis);
            return foundInRedis;
        }

        log.info("Cache miss for ID: {}", shortened);
        UrlRedirect foundInMongo = urlRepository.findById(shortened).orElse(null);

        if (foundInMongo != null) {
            log.info("Got record from MongoDB: {}, writing through to Redis", foundInMongo);
            this.setUrlRedirect(foundInMongo);
            return foundInMongo;
        }

        log.error("Could not find url redirect with shortened: {}", shortened);
        throw new FileNotFoundException("Could not find long URL for provided shortened.");
    }
}
