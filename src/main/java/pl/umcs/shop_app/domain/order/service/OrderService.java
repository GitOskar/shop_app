package pl.umcs.shop_app.domain.order.service;

import pl.umcs.shop_app.domain.order.dto.MakeOrderRequest;
import pl.umcs.shop_app.domain.order.dto.OrderDto;

public interface OrderService {

    OrderDto makeOrder(MakeOrderRequest request);
}
