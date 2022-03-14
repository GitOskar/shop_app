package pl.umcs.shop_app.domain.validation;

import pl.umcs.shop_app.domain.validation.validator.RFC5322EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RFC5322EmailValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RFC5322Email {

    String message() default "Invalid email address";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}