package me.paramoza.microlink.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

public class PublicURLValidator implements ConstraintValidator<PublicURL, String> {

    private static final Pattern PRIVATE_IP_REGEX = Pattern.compile(
            "^(10\\.|192\\.168\\.|172\\.(1[6-9]|2[0-9]|3[0-1])\\.)|127\\.0\\.0\\.1|localhost"
    );

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null) return false; // Invalid URL

            // Reject localhost, private IPs, and internal domains
            if (PRIVATE_IP_REGEX.matcher(host).find() || host.endsWith(".local") || host.endsWith(".internal")) {
                return false;
            }

            return true; // It's a public URL
        } catch (URISyntaxException e) {
            return false; // Invalid URL format
        }
    }
}
