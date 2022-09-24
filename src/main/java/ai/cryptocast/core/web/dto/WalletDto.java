package ai.cryptocast.core.web.dto;

import ai.cryptocast.core.enums.Exchange;
import com.fasterxml.jackson.annotation.JsonCreator;

public class WalletDto {
    private final String label;
    private final String apiKey;
    private final String secretKey;
    private final Exchange exchange;

    @JsonCreator
    public WalletDto(String label, String apiKey, String secretKey, Exchange exchange) {
        this.label = label;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.exchange = exchange;
    }

    public String getLabel() {
        return label;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public Exchange getExchange() {
        return exchange;
    }
}
