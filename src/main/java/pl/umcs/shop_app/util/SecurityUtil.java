package pl.umcs.shop_app.util;

import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class SecurityUtil {

    public static String getUsername() {
        return SecurityContextHolder.getContext()
                .getAuthentication().getName();
    }
}
