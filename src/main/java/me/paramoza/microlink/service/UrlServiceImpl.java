package me.paramoza.microlink.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.paramoza.microlink.data.DataAccessService;
import me.paramoza.microlink.entity.UrlRedirect;
import me.paramoza.microlink.data.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;
    private final DataAccessService dataAccessService;

    @Value("${app.shortenedUrlLength}")
    private int shortenedUrlLength;

    @Override
    public String getLongUrl(String shortened) throws ResponseStatusException, FileNotFoundException {
        UrlRedirect foundUrlRedirect = dataAccessService.getUrlRedirect(shortened);
        log.info(foundUrlRedirect.toString());

        return foundUrlRedirect.getRedirect();
    }

    @Override
    public UrlRedirect saveShortenedUrl(String longUrl) {
        String shortened = generateRandomString(shortenedUrlLength);

        String createdAt = Instant.now().toString();

        UrlRedirect urlRedirect = UrlRedirect.builder()
                .shortened(shortened)
                .redirect(longUrl)
                .createdAt(createdAt)
                .build();

        urlRepository.save(urlRedirect);
        dataAccessService.writeUrlRedirectToCache(urlRedirect);

        return urlRedirect;
    }

    public static String generateRandomString(int length) {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
