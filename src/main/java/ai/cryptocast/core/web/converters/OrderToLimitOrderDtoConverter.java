package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.web.dto.LimitOrderDto;
import com.binance.api.client.domain.account.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class OrderToLimitOrderDtoConverter implements Converter<Order, LimitOrderDto> {
    @Override
    public LimitOrderDto convert(Order source) {
        return null;
    }
}
