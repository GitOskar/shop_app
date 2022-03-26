package pl.umcs.shop_app.domain.product.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.umcs.shop_app.domain.validation.ISO4217CurrencyCode;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public abstract class BaseProductRequestDto {

    @NotEmpty
    @Length(min = 5, max = 50)
    String name;

    @NotNull
    @ISO4217CurrencyCode
    String currency;

    @NotNull
    @DecimalMin(value = "0.01")
    BigDecimal amount;

    @NotNull
    @DecimalMin(value = "0.0")
    BigDecimal quantityAvailable;
}
