package pl.umcs.shop_app.domain.order.dto;

import lombok.Value;

@Value
public class MakeOrderResponseDto {

    OrderDto orderDto;
    String redirectUrl;
    String payuStatus;
}
