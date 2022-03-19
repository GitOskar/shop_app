package pl.umcs.shop_app.security.dto;

import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Length;
import pl.umcs.shop_app.domain.validation.RFC5322Email;

import javax.validation.constraints.NotEmpty;

@Value
public class RegisterRequest {

    @NotEmpty
    @RFC5322Email
    @Length(min = 7, max = 50)
    String username;

    @ToString.Exclude
    @Length(min = 7, max = 50)
    String password;
}
