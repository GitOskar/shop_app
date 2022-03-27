package pl.umcs.shop_app.security.mapper;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.umcs.shop_app.security.dto.RegisterRequest;
import pl.umcs.shop_app.security.dto.UserDto;
import pl.umcs.shop_app.security.entity.AppUser;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class UserMapper {

    public static AppUser toEntity(RegisterRequest registerRequest) {
        return new AppUser(registerRequest.getUsername(),
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()),
                registerRequest.getPhoneNumber(),
                registerRequest.getFirstName(),
                registerRequest.getLastName(),
                CurrencyCode.valueOf(registerRequest.getSettlementCurrency()));
    }

    public static UserDto toDto(AppUser user) {
        return new UserDto(user.getId(), user.getUsername(), user.getCreatedDate(), user.getRoles().stream()
                .map(RoleMapper::toDto).collect(toCollection(ArrayList::new)));
    }
}
