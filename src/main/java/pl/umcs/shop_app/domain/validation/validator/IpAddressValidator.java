package pl.umcs.shop_app.domain.validation.validator;

import pl.umcs.shop_app.domain.validation.IpAddress;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;
import java.util.regex.Pattern;

public class IpAddressValidator implements ConstraintValidator<IpAddress, String> {

    private static final Pattern IP_ADDRESS_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$");

    @Override
    public boolean isValid(String ipAddress, ConstraintValidatorContext constraintValidatorContext) {
        return Optional.ofNullable(ipAddress)
                .map(this::matchesIpAddressRegularExpression)
                .orElse(true);
    }

    private boolean matchesIpAddressRegularExpression(String email) {
        return IP_ADDRESS_PATTERN.matcher(email).matches();
    }
}
