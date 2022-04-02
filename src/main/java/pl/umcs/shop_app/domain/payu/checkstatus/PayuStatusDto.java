package pl.umcs.shop_app.domain.payu.checkstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class PayuStatusDto {

    private String statusCode;
    private String statusDesc;
}
