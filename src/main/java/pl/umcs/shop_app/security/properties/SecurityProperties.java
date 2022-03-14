package pl.umcs.shop_app.security.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "jwtSecret")
@ConfigurationProperties(prefix = "config.security")
public class SecurityProperties {

    @NotEmpty
    private String jwtSecret;

    @NotNull
    private Long accessTokenDurationMinutes;

    @NotNull
    private Long refreshTokenDurationMinutes;
}
