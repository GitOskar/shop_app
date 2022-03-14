package pl.umcs.shop_app.security.dto;

import lombok.Value;

@Value
public class TokenPairDto {
    String accessToken;
    String refreshToken;
}
