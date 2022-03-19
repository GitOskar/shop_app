package pl.umcs.shop_app.domain.nbp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "nbp.currency-rate")
public class NbpCurrencyRateProperties {

    @NotEmpty
    private String downloadUrl;

    @NotNull
    private Long timeoutMs;
}
