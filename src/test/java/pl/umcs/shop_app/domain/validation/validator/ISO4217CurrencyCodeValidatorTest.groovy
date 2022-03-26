package pl.umcs.shop_app.domain.validation.validator

import pl.umcs.shop_app.domain.validation.ISO4217CurrencyCode
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator

class ISO4217CurrencyCodeValidatorTest extends Specification {

    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    def "should violation list be empty when request is valid"() {

        expect:
        validator.validate(new TestClass(currency)).isEmpty()

        where:
        currency << [null, 'EUR', 'USD', 'PLN', 'GBP', 'JPY']
    }

    def "should violation list be not empty when request is invalid"() {

        expect:
        !validator.validate(new TestClass(currency)).isEmpty()

        where:
        currency << ['', 'UNDEFINED', 'PL', 'gfhdfgnldf', 'US']
    }

    private class TestClass {

        TestClass(String currency) {
            this.currency = currency
        }

        @ISO4217CurrencyCode
        private String currency
    }
}
