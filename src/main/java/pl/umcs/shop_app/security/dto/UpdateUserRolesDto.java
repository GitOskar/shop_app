package pl.umcs.shop_app.security.dto;

import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.List;

@Value
public class UpdateUserRolesDto {

    @NotNull
    Long userId;
    @NotNull
    List<Long> rolesId;
}
