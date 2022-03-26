package pl.umcs.shop_app.domain.validation.validator;

import com.neovisionaries.i18n.CurrencyCode;
import pl.umcs.shop_app.domain.validation.ISO4217CurrencyCode;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

import static com.neovisionaries.i18n.CurrencyCode.UNDEFINED;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.function.Predicate.not;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class ISO4217CurrencyCodeValidator implements ConstraintValidator<ISO4217CurrencyCode, String> {

    private static final Set<String> VALID_CURRENCY_CODES = stream(CurrencyCode.values())
            .filter(not(UNDEFINED::equals)).map(Enum::name).collect(toUnmodifiableSet());

    @Override
    public boolean isValid(String currencyCode, ConstraintValidatorContext constraintValidatorContext) {

        if (isNull(currencyCode)) {
            return true;
        }

        return VALID_CURRENCY_CODES.contains(currencyCode);
    }
}
