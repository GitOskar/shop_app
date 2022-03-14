package pl.umcs.shop_app.domain.validation.validator;

import pl.umcs.shop_app.domain.validation.RFC5322Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Pattern;

public class RFC5322EmailValidator implements ConstraintValidator<RFC5322Email, String> {

    private static final Pattern EMAIL_REGEX = Pattern.compile("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(email)
                .map(this::matchesEmailRegularExpression)
                .orElse(true);
    }

    private boolean matchesEmailRegularExpression(String email) {
        return EMAIL_REGEX.matcher(email).matches();
    }
}
