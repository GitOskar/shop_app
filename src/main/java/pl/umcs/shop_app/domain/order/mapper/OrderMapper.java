package pl.umcs.shop_app.domain.order.mapper;

import lombok.NoArgsConstructor;
import pl.umcs.shop_app.domain.order.dto.OrderDto;
import pl.umcs.shop_app.domain.order.dto.OrderPartDto;
import pl.umcs.shop_app.domain.order.entity.UserOrder;

import java.util.ArrayList;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toCollection;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class OrderMapper {

    public static OrderDto toDto(UserOrder order) {

        if (isNull(order)) {
            return null;
        }

        return new OrderDto(
                order.getTotalPrice().getAmount(),
                order.getTotalPrice().getCurrencyUnit().getCode(),
                order.getOrderStatus(),
                order.getOrderParts().stream()
                        .map(part -> new OrderPartDto(
                                part.getUnitPrice().getAmount(),
                                part.getUnitPrice().getCurrencyUnit().getCode(),
                                part.getQuantity(),
                                part.getProduct().getName()))
                        .collect(toCollection(ArrayList::new)));
    }
}
