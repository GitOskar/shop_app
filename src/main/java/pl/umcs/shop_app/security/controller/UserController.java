package pl.umcs.shop_app.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.umcs.shop_app.security.dto.*;
import pl.umcs.shop_app.security.mapper.UserMapper;
import pl.umcs.shop_app.security.service.AppUserService;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final AppUserService userService;

    @GetMapping("/admin/role/list")
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        log.info("Get all roles");
        return ResponseEntity.ok(userService.findAllRules());
    }

    @PostMapping("/admin/user/search")
    public Page<UserDto> searchUserPage(@PageableDefault(sort = "id", direction = ASC) Pageable pageable,
                                        @RequestBody UserFilter userFilter) {
        log.info("Search users with filter: {}", userFilter);
        return userService.findUsers(userFilter, pageable);
    }

    @PutMapping("/admin/user/roles")
    public ResponseEntity<Object> updateUserRoles(@Valid @RequestBody UpdateUserRolesDto dto) {
        log.info("update user roles request: {}", dto);
        userService.updateRoleList(dto.getUserId(), dto.getRolesId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/public/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequest registerRequest) {
        userService.save(UserMapper.toEntity(registerRequest));
        return ResponseEntity.noContent().build();
    }
}
