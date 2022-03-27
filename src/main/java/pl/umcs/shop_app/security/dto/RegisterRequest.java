package pl.umcs.shop_app.security.dto;

import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import pl.umcs.shop_app.domain.validation.ISO4217CurrencyCode;
import pl.umcs.shop_app.domain.validation.PhoneNumber;
import pl.umcs.shop_app.domain.validation.RFC5322Email;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
public class RegisterRequest {

    @NotEmpty
    @RFC5322Email
    @Length(min = 7, max = 50)
    String username;

    @ToString.Exclude
    @Length(min = 7, max = 50)
    String password;

    @NotNull
    @PhoneNumber
    String phoneNumber;

    @NotEmpty
    @Length(min = 3, max = 50)
    String firstName;

    @NotEmpty
    @Length(min = 3, max = 50)
    String lastName;

    @NotNull
    @ISO4217CurrencyCode
    String settlementCurrency;
}
