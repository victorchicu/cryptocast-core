package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.Wallet;
import ai.cryptocast.core.web.dto.WalletDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToWalletConverter implements Converter<WalletDto, Wallet> {
    @Override
    public Wallet convert(WalletDto source) {
        return Wallet.newBuilder()
                .label(source.getLabel())
                .apiKey(source.getApiKey())
                .secretKey(source.getSecretKey())
                .exchange(source.getExchange())
                .build();
    }
}
