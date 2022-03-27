package pl.umcs.shop_app.domain.validation;

import pl.umcs.shop_app.domain.validation.validator.IpAddressValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IpAddressValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAddress {

    String message() default "Invalid ip address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
