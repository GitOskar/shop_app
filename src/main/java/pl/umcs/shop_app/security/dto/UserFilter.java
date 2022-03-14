package pl.umcs.shop_app.security.dto;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class UserFilter {
    String username;
    LocalDateTime createdDateFrom;
    LocalDateTime createdDateTo;
}
