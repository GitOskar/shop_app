package pl.umcs.shop_app.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umcs.shop_app.domain.order.dto.*;
import pl.umcs.shop_app.domain.order.service.OrderService;
import pl.umcs.shop_app.util.SecurityUtil;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/make-order")
    public Mono<ResponseEntity<MakeOrderResponseDto>> makeOrder(@Valid @RequestBody MakeOrderRequest request) {
        log.info("Make order request: {}", request);
        return orderService.makeOrder(request)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/orders/search")
    public ResponseEntity<Page<OrderShortDto>> searchOrders(@Valid @RequestBody OrderFilter filter,
                                                            @PageableDefault(sort = "id", direction = DESC) Pageable pageable) {
        log.info("Search orders filter: {}", filter);
        String username = SecurityUtil.getUsername();
        filter.setUsername(username);

        Page<OrderShortDto> orderPage = orderService.searchOrders(filter, pageable);

        return ResponseEntity.ok(orderPage);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable Long orderId) {
        log.info("Get order orderId: {}", orderId);
        String username = SecurityUtil.getUsername();
        OrderDto order = orderService.findByUsernameAndOrderId(username, orderId);
        return ResponseEntity.ok(order);
    }
}
