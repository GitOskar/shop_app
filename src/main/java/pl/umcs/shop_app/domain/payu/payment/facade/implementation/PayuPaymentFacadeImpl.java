package pl.umcs.shop_app.domain.payu.payment.facade.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentRequest;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentResponse;
import pl.umcs.shop_app.domain.payu.payment.facade.PayuPaymentFacade;
import pl.umcs.shop_app.domain.payu.properties.PayuProperties;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayuPaymentFacadeImpl implements PayuPaymentFacade {

    private final WebClient payuWebClient;
    private final PayuProperties payuProperties;

    @Override
    public Mono<PayuPaymentResponse> sendTransaction(UserOrder order) {

        return payuWebClient.post()
                .uri(payuProperties.getPaymentUrl())
                .body(BodyInserters.fromValue(PayuPaymentRequest.from(order, payuProperties.getMerchantPosId())))
                .exchangeToMono(this::wrapResponse)
                .doOnNext(response -> log.info("PayU response: {}", response));
    }

    private Mono<PayuPaymentResponse> wrapResponse(ClientResponse clientResponse) {

        HttpStatus status = clientResponse.statusCode();

        if (status.is4xxClientError()) {
            log.error("PayU validation exception. Some information are missing or incorrect.");
        } else if (status.is5xxServerError()) {
            log.error("Error PayU connection");
        }

        return clientResponse.bodyToMono(PayuPaymentResponse.class);
    }
}
