package pl.umcs.shop_app.security.dto;

import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
public class UserDto {

    Long id;
    String username;
    LocalDateTime createdDate;
    List<RoleDto> roles;
}
