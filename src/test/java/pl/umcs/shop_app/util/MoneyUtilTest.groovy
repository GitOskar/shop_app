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

    def "should correctly return money from currency code and big decimal amount"() {

        expect:
        MoneyUtil.toMoney(currencyCode, amount) == expectedMoney

        where:
        currencyCode     | amount                     || expectedMoney
        null             | null                       || null
        CurrencyCode.USD | null                       || null
        null             | BigDecimal.TEN             || null
        CurrencyCode.USD | BigDecimal.TEN             || Money.of(CurrencyUnit.USD, BigDecimal.TEN)
        CurrencyCode.GBP | BigDecimal.valueOf(21.2)   || Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(21.2))
        CurrencyCode.EUR | BigDecimal.valueOf(666.69) || Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(666.69))
    }
}
