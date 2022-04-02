package pl.umcs.shop_app.domain.order.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.umcs.shop_app.domain.order.dto.*;
import reactor.core.publisher.Mono;

public interface OrderService {

    Mono<MakeOrderResponseDto> makeOrder(MakeOrderRequest request);
    OrderDto findByUsernameAndOrderId(String username, Long orderId);
    Page<OrderShortDto> searchOrders(OrderFilter orderFilter, Pageable pageable);
}
