package pl.umcs.shop_app.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.umcs.shop_app.security.dto.RoleDto;
import pl.umcs.shop_app.security.entity.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select new pl.umcs.shop_app.security.dto.RoleDto(id, name) from Role")
    List<RoleDto> findAllRoleDtos();
}
