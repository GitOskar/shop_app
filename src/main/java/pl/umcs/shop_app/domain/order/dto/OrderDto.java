package pl.umcs.shop_app.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private BigDecimal totalPriceAmount;
    private String totalPriceCurrency;
    private OrderStatus orderStatus;
    private List<OrderPartDto> orderParts;
}
