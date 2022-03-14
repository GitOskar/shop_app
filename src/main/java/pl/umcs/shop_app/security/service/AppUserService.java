package pl.umcs.shop_app.security.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.umcs.shop_app.security.dto.RoleDto;
import pl.umcs.shop_app.security.dto.UserDto;
import pl.umcs.shop_app.security.dto.UserFilter;
import pl.umcs.shop_app.security.entity.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser save(AppUser appUser);

    List<RoleDto> findAllRules();

    void updateRoleList(Long userId, List<Long> roleIds);

    Page<UserDto> findUsers(UserFilter filter, Pageable pageable);
}
