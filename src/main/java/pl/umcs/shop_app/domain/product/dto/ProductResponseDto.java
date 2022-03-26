package pl.umcs.shop_app.domain.product.dto;

import com.neovisionaries.i18n.CurrencyCode;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.money.Money;

import java.math.BigDecimal;

import static java.util.Objects.nonNull;
import static pl.umcs.shop_app.util.MoneyUtil.moneyToCurrencyCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseDto {

    private Long id;
    private String name;
    private BigDecimal amount;
    private CurrencyCode currency;
    private BigDecimal quantityAvailable;

    @QueryProjection
    public ProductResponseDto(Long id, String name, Money price, BigDecimal quantityAvailable) {
        this.id = id;
        this.name = name;

        if (nonNull(price)) {
            this.amount = price.getAmount();
            this.currency = moneyToCurrencyCode(price);
        }
        this.quantityAvailable = quantityAvailable;
    }
}
