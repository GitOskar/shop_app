package pl.umcs.shop_app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umcs.shop_app.security.entity.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    boolean existsAppUserByUsername(String username);
}
