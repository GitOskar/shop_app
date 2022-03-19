package pl.umcs.shop_app.domain.currencyexchanger.implementation

import com.neovisionaries.i18n.CurrencyCode
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import pl.umcs.shop_app.domain.currencyexchanger.CurrencyExchanger
import pl.umcs.shop_app.domain.exception.ApiException
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate
import pl.umcs.shop_app.domain.nbp.currency.repository.NbpCurrencyRateRepository
import spock.lang.Specification

import java.time.LocalDate

import static pl.umcs.shop_app.domain.exception.ErrorStatus.ERROR_DURING_CURRENCY_EXCHANGING

class SimpleNbpCurrencyExchangerTest extends Specification {

    NbpCurrencyRateRepository nbpCurrencyRateRepository = Mock(NbpCurrencyRateRepository)
    CurrencyExchanger currencyExchanger = new SimpleNbpCurrencyExchanger(nbpCurrencyRateRepository)

    def "should not use repository where src and dst currencies are the same"() {

        given:
        Money toExchange = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(21.64))
        CurrencyCode dstCurrency = CurrencyCode.USD

        when:
        Money result = currencyExchanger.exchange(toExchange, dstCurrency)

        then:
        0 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(_ as CurrencyCode)
        result == toExchange
    }

    def "should correctly exchange currencies from PLN"() {

        given:
        Money toExchange = Money.of(CurrencyUnit.of("PLN"), BigDecimal.valueOf(21.64))
        CurrencyCode dstCurrency = CurrencyCode.USD

        when:
        Money result = currencyExchanger.exchange(toExchange, dstCurrency)

        then:
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.USD)
                >> Optional.of(new NbpCurrencyRate(LocalDate.now(), CurrencyCode.USD, BigDecimal.valueOf(3.95)))
        0 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(_ as CurrencyCode)
        result == Money.of(CurrencyUnit.USD, BigDecimal.valueOf(5.48))

    }

    def "should correctly exchange currencies to PLN"() {

        given:
        Money toExchange = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(10.42))
        CurrencyCode dstCurrency = CurrencyCode.PLN

        when:
        Money result = currencyExchanger.exchange(toExchange, dstCurrency)

        then:
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.USD)
                >> Optional.of(new NbpCurrencyRate(LocalDate.now(), CurrencyCode.USD, BigDecimal.valueOf(3.95)))
        0 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(_ as CurrencyCode)
        result == Money.of(CurrencyUnit.of("PLN"), BigDecimal.valueOf(41.16))
    }

    def "should correctly exchange currencies"() {

        given:
        Money toExchange = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(10.42))
        CurrencyCode dstCurrency = CurrencyCode.GBP

        when:
        Money result = currencyExchanger.exchange(toExchange, dstCurrency)

        then:
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.USD)
                >> Optional.of(new NbpCurrencyRate(LocalDate.now(), CurrencyCode.USD, BigDecimal.valueOf(3.95)))
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.GBP)
                >> Optional.of(new NbpCurrencyRate(LocalDate.now(), CurrencyCode.USD, BigDecimal.valueOf(5.12)))
        result == Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(8.04))
    }

    def "should throw exception when currency rate is not found"() {

        given:
        Money toExchange = Money.of(CurrencyUnit.USD, BigDecimal.valueOf(10.42))
        CurrencyCode dstCurrency = CurrencyCode.GBP

        when:
        currencyExchanger.exchange(toExchange, dstCurrency)

        then:
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.USD)
                >> Optional.of(new NbpCurrencyRate(LocalDate.now(), CurrencyCode.USD, BigDecimal.valueOf(3.95)))
        1 * nbpCurrencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.GBP)
                >> Optional.empty()
        ApiException exception = thrown()
        exception.getErrorStatus() == ERROR_DURING_CURRENCY_EXCHANGING
    }
}
