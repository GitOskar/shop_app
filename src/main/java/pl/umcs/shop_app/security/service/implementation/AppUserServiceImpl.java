package pl.umcs.shop_app.security.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.security.dto.RoleDto;
import pl.umcs.shop_app.security.dto.UserDto;
import pl.umcs.shop_app.security.dto.UserFilter;
import pl.umcs.shop_app.security.entity.AppUser;
import pl.umcs.shop_app.security.entity.Role;
import pl.umcs.shop_app.security.mapper.UserMapper;
import pl.umcs.shop_app.security.repository.AppUserQueryDslRepository;
import pl.umcs.shop_app.security.repository.AppUserRepository;
import pl.umcs.shop_app.security.repository.RoleRepository;
import pl.umcs.shop_app.security.service.AppUserService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toCollection;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.USER_ALREADY_EXISTS;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.USER_NOT_FOUND_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final AppUserQueryDslRepository appUserQueryDslRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(toCollection(ArrayList::new));

        return new User(user.getUsername(), user.getPasswordHash(), authorities);
    }

    @Override
    public AppUser save(AppUser appUser) {
        if (appUserRepository.existsAppUserByUsername(appUser.getUsername())) {
            log.error("User: {} already exists.", appUser.getUsername());
            throw new ApiException(USER_ALREADY_EXISTS);
        }
        log.info("Saving new user: {}", appUser);
        return appUserRepository.save(appUser);
    }

    @Override
    public List<RoleDto> findAllRules() {
        return roleRepository.findAllRoleDtos();
    }

    @Override
    @Transactional
    public void updateRoleList(Long userId, List<Long> roleIds) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND_EXCEPTION));

        List<Role> roles = roleRepository.findAllById(roleIds);

        log.info("Update user: {} with roles: {}", user, roles);

        Set<Role> userRoles = user.getRoles();

        userRoles.clear();
        userRoles.addAll(roles);
    }

    @Override
    public Page<UserDto> findUsers(UserFilter filter, Pageable pageable) {
        Page<AppUser> pages = appUserQueryDslRepository.findUsers(filter, pageable);

        return new PageImpl<>(pages.getContent().stream().map(UserMapper::toDto).collect(toCollection(ArrayList::new)),
                pageable, pages.getTotalElements());
    }
}
