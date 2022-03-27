package pl.umcs.shop_app.domain.payu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuPaymentResponse {

    private PayuPaymentResponseStatus status;
    private String orderId;
    private String redirectUri;
}
