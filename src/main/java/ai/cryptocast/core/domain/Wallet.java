package ai.cryptocast.core.domain;

import ai.cryptocast.core.enums.Exchange;

public class Wallet {
    private String label;
    private String apiKey;
    private String secretKey;
    private Exchange exchange;

    private Wallet(Builder builder) {
        setLabel(builder.label);
        setApiKey(builder.apiKey);
        setSecretKey(builder.secretKey);
        setExchange(builder.exchange);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Exchange getExchange() {
        return exchange;
    }

    public void setExchange(Exchange exchange) {
        this.exchange = exchange;
    }

    public static final class Builder {
        private String label;
        private String apiKey;
        private String secretKey;
        private Exchange exchange;

        private Builder() {}

        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Builder apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this;
        }

        public Builder secretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Builder exchange(Exchange exchange) {
            this.exchange = exchange;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }
    }
}
