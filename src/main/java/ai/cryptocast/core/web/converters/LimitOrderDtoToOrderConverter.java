package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.web.dto.LimitOrderDto;
import ai.cryptocast.core.web.dto.OrderDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class LimitOrderDtoToOrderConverter implements Converter<LimitOrderDto, OrderDto> {
    @Override
    public OrderDto convert(LimitOrderDto source) {
        return null;
    }
}
