package com.crypto.core.exchanges.kucoin.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kucoin")
public class KuCoinProperties {
}
