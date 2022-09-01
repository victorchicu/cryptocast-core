package ai.cryptocast.core.web.converters;

import ai.cryptocast.core.domain.TestOrder;
import ai.cryptocast.core.web.dto.OrderRequestDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToTestOrderConverter implements Converter<OrderRequestDto, TestOrder> {
    @Override
    public TestOrder convert(OrderRequestDto source) {
        return new TestOrder()
                .setSide(source.getSide())
                .setType(source.getType())
                .setPrice(source.getPrice())
                .setQuantity(source.getQuantity());
    }
}
