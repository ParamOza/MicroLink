package me.paramoza.microlink.service;

import me.paramoza.microlink.entity.UrlRedirect;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileNotFoundException;

public interface UrlService {
    String getLongUrl(String shortened) throws ResponseStatusException, FileNotFoundException;
    UrlRedirect saveShortenedUrl(String longUrl);
}
