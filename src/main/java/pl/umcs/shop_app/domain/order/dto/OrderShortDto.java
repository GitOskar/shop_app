package pl.umcs.shop_app.domain.order.dto;

import com.neovisionaries.i18n.CurrencyCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;
import pl.umcs.shop_app.util.MoneyUtil;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderShortDto {

    private Long id;
    private BigDecimal totalPriceAmount;
    private CurrencyCode totalPriceCurrency;
    private OrderStatus orderStatus;

    @QueryProjection
    public OrderShortDto(Long id, Money totalPrice, OrderStatus orderStatus) {
        this.id = id;
        this.totalPriceAmount = totalPrice.getAmount();
        this.totalPriceCurrency = MoneyUtil.moneyToCurrencyCode(totalPrice);
        this.orderStatus = orderStatus;
    }
}
