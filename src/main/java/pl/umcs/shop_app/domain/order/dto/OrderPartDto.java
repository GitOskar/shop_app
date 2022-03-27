package pl.umcs.shop_app.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPartDto {

    private BigDecimal priceAmount;
    private String priceCurrency;
    private BigDecimal quantity;
    private String productName;
}
