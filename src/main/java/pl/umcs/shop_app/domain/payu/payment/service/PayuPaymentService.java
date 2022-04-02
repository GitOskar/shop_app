package pl.umcs.shop_app.domain.payu.payment.service;

import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentResponse;
import reactor.core.publisher.Mono;

public interface PayuPaymentService {

    Mono<PayuPaymentResponse> sendTransaction(UserOrder order);
}
