package pl.umcs.shop_app.domain.payu.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuBuyer {

    private String email;
    private String phone;
    private String firstName;
    private String lastName;
}
