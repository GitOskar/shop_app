package pl.umcs.shop_app.domain.validation.validator;

import pl.umcs.shop_app.domain.validation.PhoneNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Pattern;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumber, String> {

    private static final Pattern PHONE_NUMBER_REGEX = Pattern.compile("\\d{9}");

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(phoneNumber)
                .map(this::matchesPhoneNumberRegularExpression)
                .orElse(true);
    }

    private boolean matchesPhoneNumberRegularExpression(String email) {
        return PHONE_NUMBER_REGEX.matcher(email).matches();
    }
}
