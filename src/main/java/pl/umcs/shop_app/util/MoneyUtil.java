package pl.umcs.shop_app.util;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.NoArgsConstructor;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

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
}
