package ai.cryptocast.core.domain.converters;

import ai.cryptocast.core.domain.exceptions.IllegalOrderTypeException;
import ai.cryptocast.core.configs.BinanceConfig;
import com.binance.api.client.domain.TimeInForce;
import com.binance.api.client.domain.account.NewOrder;
import ai.cryptocast.core.domain.TestOrder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToNewConverter implements Converter<TestOrder, NewOrder> {
    private final BinanceConfig binanceConfig;

    public OrderToNewConverter(BinanceConfig binanceConfig) {
        this.binanceConfig = binanceConfig;
    }

    @Override
    public NewOrder convert(TestOrder source) {
        String symbol = source.getAsset(); //toSymbol(source.getAsset());
        switch (source.getType()) {
            case LIMIT -> {
                return switch (source.getSide()) {
                    case BUY -> NewOrder.limitBuy(
                            symbol,
                            TimeInForce.GTC,
                            source.getQuantity().toString(),
                            source.getPrice().toString()
                    );
                    case SELL -> NewOrder.limitSell(
                            symbol,
                            TimeInForce.GTC,
                            source.getQuantity().toString(),
                            source.getPrice().toString()
                    );
                };
            }
            case MARKET -> {
                return switch (source.getSide()) {
                    case BUY -> NewOrder.marketBuy(
                            symbol,
                            source.getQuantity().toString()
                    );
                    case SELL -> NewOrder.marketSell(
                            symbol,
                            source.getQuantity().toString()
                    );
                };
            }
            default -> throw new IllegalOrderTypeException(source.getType());
        }
    }
}
