package pl.umcs.shop_app.util

import com.neovisionaries.i18n.CurrencyCode
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import spock.lang.Specification

class MoneyUtilTest extends Specification {

    def "should correctly extract currency code from money object"() {
        expect:
        code == MoneyUtil.moneyToCurrencyCode(money)

        where:
        money                                            || code
        null                                             || null
        Money.of(CurrencyUnit.USD, BigDecimal.ONE)       || CurrencyCode.USD
        Money.of(CurrencyUnit.GBP, BigDecimal.ONE)       || CurrencyCode.GBP
        Money.of(CurrencyUnit.of("PLN"), BigDecimal.ONE) || CurrencyCode.PLN
        Money.of(CurrencyUnit.EUR, BigDecimal.ONE)       || CurrencyCode.EUR
    }

    def "should correctly map currency code to currency unit"() {
        expect:
        currencyUnit == MoneyUtil.currencyCodeToCurrencyUnit(currencyCode)

        where:
        currencyCode     || currencyUnit
        null             || null
        CurrencyCode.USD || CurrencyUnit.USD
        CurrencyCode.GBP || CurrencyUnit.GBP
        CurrencyCode.PLN || CurrencyUnit.of("PLN")
        CurrencyCode.EUR || CurrencyUnit.EUR
    }
}
