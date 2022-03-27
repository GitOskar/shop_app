package pl.umcs.shop_app.domain.payu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuProduct {
    private String name;
    private String unitPrice;
    private String quantity;
}
