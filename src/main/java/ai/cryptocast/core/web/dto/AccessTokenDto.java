package ai.cryptocast.core.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;

public class AccessTokenDto {
    private final String accessToken;

    @JsonCreator
    public AccessTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
