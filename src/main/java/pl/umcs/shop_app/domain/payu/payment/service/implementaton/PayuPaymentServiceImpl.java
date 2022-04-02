package pl.umcs.shop_app.domain.payu.payment.service.implementaton;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;
import pl.umcs.shop_app.domain.payu.payment.dto.PayuPaymentResponse;
import pl.umcs.shop_app.domain.payu.payment.facade.PayuPaymentFacade;
import pl.umcs.shop_app.domain.payu.payment.service.PayuPaymentService;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PayuPaymentServiceImpl implements PayuPaymentService {

    private static final String SUCCESS_STATUS = "SUCCESS";

    private final PayuPaymentFacade payuPaymentFacade;

    @Override
    public Mono<PayuPaymentResponse> sendTransaction(UserOrder order) {

        log.info("Send transaction to PayU, id: {}", order.getId());

        return payuPaymentFacade.sendTransaction(order)
                .doOnNext(response -> setOrderData(order, response));
    }

    private void setOrderData(UserOrder order, PayuPaymentResponse response) {

        order.setOrderId(response.getOrderId());

        if (Objects.equals(SUCCESS_STATUS, response.getStatus().getStatusCode())) {
            order.setOrderStatus(OrderStatus.UNPAID);
        } else {
            order.setOrderStatus(OrderStatus.ERROR);
        }
    }
}
