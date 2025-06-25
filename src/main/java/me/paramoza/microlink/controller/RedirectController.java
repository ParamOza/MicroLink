package me.paramoza.microlink.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.paramoza.microlink.entity.UrlRedirect;
import me.paramoza.microlink.request.CreateUrlRedirectRequest;
import me.paramoza.microlink.response.CreateUrlRedirectResponse;
import me.paramoza.microlink.service.UrlService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class RedirectController {

    @Value("${app.baseUrl}")
    private String appBaseUrl;

    private final UrlService urlService;

    @GetMapping("/{shortened}")
    public RedirectView redirect(@PathVariable String shortened) {
        String longUrl;
        try {
            longUrl = urlService.getLongUrl(shortened);
        } catch (FileNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(longUrl);

        return redirectView;
    }

    @PostMapping("/shorten")
    public CreateUrlRedirectResponse createShortUrl(@Valid @RequestBody CreateUrlRedirectRequest request) {
        String longUrl = request.getUrl();

        UrlRedirect urlRedirect = urlService.saveShortenedUrl(longUrl);

        return CreateUrlRedirectResponse.builder()
                .url(longUrl)
                .shortened(appBaseUrl + urlRedirect.getShortened())
                .build();
    }
}
