package pl.umcs.shop_app.domain.payu.checkstatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.umcs.shop_app.domain.payu.properties.PayuProperties;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckStatusFacadeImpl implements CheckStatusFacade {

    private final WebClient payuWebClient;
    private final PayuProperties payuProperties;

    @Override
    public Mono<PayuCheckStatusResponse> checkStatus(String orderId) {

        return payuWebClient.get()
                .uri(payuProperties.getCheckStatusUrl() + "/" + orderId)
                .exchangeToMono(this::wrapResponse)
                .doOnNext(response -> response.setOrderId(orderId))
                .doOnNext(response -> log.info("PayU response: {}", response));
    }

    private Mono<PayuCheckStatusResponse> wrapResponse(ClientResponse clientResponse) {

        HttpStatus status = clientResponse.statusCode();

        if (status.is4xxClientError()) {
            log.error("PayU validation exception. Some information are missing or incorrect.");
        } else if (status.is5xxServerError()) {
            log.error("Error PayU connection");
        }

        return clientResponse.bodyToMono(PayuCheckStatusResponse.class);
    }
}
