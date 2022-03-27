package pl.umcs.shop_app.domain.payu.properties;

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
@ToString(exclude = "accessToken")
@ConfigurationProperties(prefix = "payu")
public class PayuProperties {

    @NotNull
    private String accessToken;

    @NotEmpty
    private String paymentUrl;

    @NotNull
    private String merchantPosId;

    @NotNull
    private Long timeoutMs;
}
