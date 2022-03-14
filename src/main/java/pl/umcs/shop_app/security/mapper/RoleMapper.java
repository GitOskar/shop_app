package pl.umcs.shop_app.security.mapper;

import lombok.NoArgsConstructor;
import pl.umcs.shop_app.security.dto.RoleDto;
import pl.umcs.shop_app.security.entity.Role;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class RoleMapper {

    public static RoleDto toDto(Role role) {
        return new RoleDto(role.getId(), role.getName());
    }
}
