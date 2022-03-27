package pl.umcs.shop_app.domain.order.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.shop_app.domain.order.dto.MakeOrderRequest;
import pl.umcs.shop_app.domain.order.dto.OrderDto;
import pl.umcs.shop_app.domain.order.service.OrderService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/make-order")
    public ResponseEntity<OrderDto> makeOrder(@Valid @RequestBody MakeOrderRequest request) {
        log.info("Make order request: {}", request);
        OrderDto response = orderService.makeOrder(request);
        return ResponseEntity.ok(response);
    }
}
