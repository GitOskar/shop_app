package pl.umcs.shop_app.domain.payu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuPaymentResponseStatus {

    private String statusCode;
    private String severity;
    private String code;
    private String codeLiteral;
    private String statusDesc;
}
