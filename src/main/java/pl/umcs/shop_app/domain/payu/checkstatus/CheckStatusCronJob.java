package pl.umcs.shop_app.domain.payu.checkstatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;
import pl.umcs.shop_app.domain.order.repository.OrderRepository;
import pl.umcs.shop_app.domain.payu.properties.PayuProperties;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckStatusCronJob {

    private final OrderRepository orderRepository;
    private final PayuProperties payuProperties;
    private final CheckStatusFacade facade;

    @Scheduled(cron = "${config.cron.payu-check-status}")
    public void nbpCurrencyRateCronJob() {

        log.info("[START] PayU check status cron job");

        LocalDateTime checkTo = LocalDateTime.now().minusSeconds(payuProperties.getCheckToSec());

        Flux.fromIterable(orderRepository.findAllOrderIdsAfterDateAndStatus(checkTo))
                .flatMap(facade::checkStatus)
                .doOnNext(this::updateStatus)
                .onErrorContinue((throwable, o) -> log.error("Error occurred during check status method", throwable))
                .subscribe();
    }

    private void updateStatus(PayuCheckStatusResponse response) {

        Optional.ofNullable(response.getOrders())
                .flatMap(orders -> orders.stream().findFirst())
                .map(CheckStatusOrderDto::getStatus)
                .flatMap(this::mapToApiStatus)
                .ifPresent(status -> orderRepository.updateStatus(response.getOrderId(), status));
    }

    private Optional<OrderStatus> mapToApiStatus(String status) {

        OrderStatus orderStatus = switch (status) {
            case "COMPLETED" -> OrderStatus.PAID;
            default -> null;
        };


        return Optional.ofNullable(orderStatus);
    }
}
