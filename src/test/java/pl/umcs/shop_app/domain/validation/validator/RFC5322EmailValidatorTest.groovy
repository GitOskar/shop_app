package pl.umcs.shop_app.domain.validation.validator


import pl.umcs.shop_app.domain.validation.RFC5322Email
import spock.lang.Specification

import javax.validation.Validation
import javax.validation.Validator

class RFC5322EmailValidatorTest extends Specification {


    Validator validator = Validation.buildDefaultValidatorFactory().getValidator()

    def "should violation list be empty when request is valid"() {

        expect:
        validator.validate(new TestClass(email)).isEmpty()

        where:
        email << [null, 'user@domain.pl', 'ontact@domain.com', 'contact.us@domain.com', 'contact-us+1@domain.com', 'contact?us+1@domain.com']
    }

    def "should violation list be not empty when request is invalid"() {

        expect:
        !validator.validate(new TestClass(email)).isEmpty()

        where:
        email << ['', '  ', 'user @domain.pl', '@domain.pl']
    }

    private class TestClass {

        TestClass(String email) {
            this.email = email
        }

        @RFC5322Email
        private String email
    }
}
