package pl.umcs.shop_app.domain.payu.payment.dto;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuPaymentRequest {

    private String customerIp;
    private String merchantPosId;
    private String description;
    private CurrencyCode currencyCode;
    private String totalAmount;
    private PayuBuyer buyer;
    private List<PayuProduct> products;
}
