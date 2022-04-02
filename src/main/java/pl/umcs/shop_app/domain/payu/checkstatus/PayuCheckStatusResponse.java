package pl.umcs.shop_app.domain.payu.checkstatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class PayuCheckStatusResponse {

    private String orderId;
    private List<CheckStatusOrderDto> orders;
    private PayuStatusDto status;
}
