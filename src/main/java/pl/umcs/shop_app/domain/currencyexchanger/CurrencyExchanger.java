package pl.umcs.shop_app.domain.currencyexchanger;

import com.neovisionaries.i18n.CurrencyCode;
import org.joda.money.Money;

public interface CurrencyExchanger {

    Money exchange(Money source, CurrencyCode destinationCurrency);
}
