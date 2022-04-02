package pl.umcs.shop_app.domain.payu.checkstatus;

import reactor.core.publisher.Mono;

public interface CheckStatusFacade {

    Mono<PayuCheckStatusResponse> checkStatus(String orderId);
}
