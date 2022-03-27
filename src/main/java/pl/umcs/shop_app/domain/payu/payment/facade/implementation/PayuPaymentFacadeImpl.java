package pl.umcs.shop_app.domain.payu.payment.facade.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.umcs.shop_app.domain.order.entity.OrderPart;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuBuyer;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentRequest;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentResponse;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuProduct;
import pl.umcs.shop_app.domain.payu.payment.facade.PayuPaymentFacade;
import pl.umcs.shop_app.domain.payu.properties.PayuProperties;
import pl.umcs.shop_app.security.entity.AppUser;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayuPaymentFacadeImpl implements PayuPaymentFacade {

    private final WebClient payuWebClient;
    private final PayuProperties payuProperties;

    public Mono<PayuPaymentResponse> sendTransaction(UserOrder order) {

        return payuWebClient.post()
                .body(BodyInserters.fromValue(preparePayuRequest(order)))
                .exchangeToMono(this::wrapResponse)
                .doOnNext(a -> log.info("{}", a)); //todo remove
    }

    private Mono<PayuPaymentResponse> wrapResponse(ClientResponse clientResponse) {

        HttpStatus status = clientResponse.statusCode();

        if (status.is4xxClientError()) {
            log.error("PayU validation exception. Some informations are missing or incorrect.");
        } else if (status.is5xxServerError()) {
            log.error("Error PayU connection");
        }

        return clientResponse.bodyToMono(PayuPaymentResponse.class);
    }

    private PayuPaymentRequest preparePayuRequest(UserOrder order) {

        AppUser user = order.getUser();

        return new PayuPaymentRequest(
                order.getCustomerIp(),
                payuProperties.getMerchantPosId(),
                "Order number " + order.getId(),
                user.getSettlementCurrency(),
                Long.toString(order.getTotalPrice().getAmountMinorLong()),
                prepareBuyer(order),
                prepareProducts(order.getOrderParts())
        );
    }

    private List<PayuProduct> prepareProducts(Collection<OrderPart> orderParts) {

        return orderParts.stream()
                .map(part -> new PayuProduct(part.getProduct().getName(),
                        Long.toString(part.getUnitPrice().getAmountMinorLong()),
                        Long.toString(part.getQuantity().longValue())))
                .collect(toCollection(ArrayList::new));
    }

    private PayuBuyer prepareBuyer(UserOrder order) {

        AppUser user = order.getUser();

        return new PayuBuyer(user.getUsername(), user.getPhoneNumber(), user.getFirstName(), user.getLastName());
    }
}
