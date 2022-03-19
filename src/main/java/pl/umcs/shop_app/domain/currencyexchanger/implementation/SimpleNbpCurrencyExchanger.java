package pl.umcs.shop_app.domain.currencyexchanger.implementation;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Component;
import pl.umcs.shop_app.domain.currencyexchanger.CurrencyExchanger;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;
import pl.umcs.shop_app.domain.nbp.currency.repository.NbpCurrencyRateRepository;

import java.math.BigDecimal;
import java.util.Objects;

import static com.neovisionaries.i18n.CurrencyCode.PLN;
import static java.math.BigDecimal.ONE;
import static java.math.RoundingMode.HALF_UP;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.ERROR_DURING_CURRENCY_EXCHANGING;
import static pl.umcs.shop_app.util.MoneyUtil.currencyCodeToCurrencyUnit;
import static pl.umcs.shop_app.util.MoneyUtil.moneyToCurrencyCode;

@Slf4j
@Component
@RequiredArgsConstructor
public class SimpleNbpCurrencyExchanger implements CurrencyExchanger {

    private final NbpCurrencyRateRepository currencyRateRepository;

    @Override
    public Money exchange(Money source, CurrencyCode dstCurrencyCode) {

        CurrencyCode srcCurrencyCode = moneyToCurrencyCode(source);

        if (Objects.equals(srcCurrencyCode, dstCurrencyCode)) {
            return source;
        }

        CurrencyUnit dstCurrencyUnit = currencyCodeToCurrencyUnit(dstCurrencyCode);

        BigDecimal conversionFlow = calculateCurrencyRate(srcCurrencyCode, dstCurrencyCode);

        return source.convertedTo(dstCurrencyUnit, conversionFlow, HALF_UP);
    }

    private BigDecimal calculateCurrencyRate(CurrencyCode src, CurrencyCode dst) {
        BigDecimal srcToPln = findCurrencyRate(src);
        BigDecimal plnToDst = ONE.divide(findCurrencyRate(dst), 7, HALF_UP);

        return srcToPln.multiply(plnToDst);
    }

    private BigDecimal findCurrencyRate(CurrencyCode currency) {

        return Objects.equals(currency, PLN)
                ? ONE
                : currencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(currency)
                .map(NbpCurrencyRate::getRate)
                .orElseThrow(() -> {
                    log.error("Currency {} not found in database", currency);
                    return new ApiException(ERROR_DURING_CURRENCY_EXCHANGING);
                });
    }
}
