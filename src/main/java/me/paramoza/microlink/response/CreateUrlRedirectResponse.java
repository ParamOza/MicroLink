package me.paramoza.microlink.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUrlRedirectResponse {
    private String url;
    private String shortened;
}
