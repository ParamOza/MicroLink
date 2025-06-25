package me.paramoza.microlink.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import me.paramoza.microlink.validation.PublicURL;

@Data
@Builder
public class CreateUrlRedirectRequest {
    @NotBlank(message = "URL cannot be blank")
    @PublicURL
    private String url;
}
