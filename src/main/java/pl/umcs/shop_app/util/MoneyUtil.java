package pl.umcs.shop_app.util;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@Slf4j
@NoArgsConstructor(access = PRIVATE)
public class MoneyUtil {

    public static CurrencyCode moneyToCurrencyCode(Money money) {

        return Optional.ofNullable(money)
                .map(Money::getCurrencyUnit)
                .map(CurrencyUnit::getCode)
                .map(CurrencyCode::valueOf)
                .orElse(null);
    }

    public static CurrencyUnit currencyCodeToCurrencyUnit(CurrencyCode currencyCode) {
        return Optional.ofNullable(currencyCode)
                .map(Enum::name)
                .map(CurrencyUnit::of)
                .orElse(null);
    }

    public static Money toMoney(CurrencyCode currencyCode, BigDecimal amount) {

        if (isNull(currencyCode) || isNull(amount)) {
            log.error("Cannot convert to money when currencyCode or amount is null. currencyCode: {}, amount: {}", currencyCode, amount);
            return null;
        }

        return Money.of(currencyCodeToCurrencyUnit(currencyCode), amount);
    }
}
