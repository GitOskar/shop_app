package pl.umcs.shop_app.security.mapper

import com.neovisionaries.i18n.CurrencyCode
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import pl.umcs.shop_app.security.dto.RegisterRequest
import spock.lang.Specification

class UserMapperTest extends Specification {

    def "should map request to entity correctly"() {

        given:
        def passwordEncoder = new BCryptPasswordEncoder()

        when:
        def entity = UserMapper.toEntity(request)

        then:
        entity.getUsername() == request.getUsername()
        passwordEncoder.matches(request.getPassword(), entity.getPasswordHash())
        entity.getFirstName() == request.getFirstName()
        entity.getLastName() == request.getLastName()
        entity.getPhoneNumber() == request.getPhoneNumber()
        entity.getSettlementCurrency() == CurrencyCode.valueOf(request.getSettlementCurrency())

        where:
        request << [new RegisterRequest("user@domain.pl", "password", "512511121", "Firstname", "Lastname", "EUR"),
                    new RegisterRequest("user23@domain11.com.pl", "zaq1@WSX", "501321542", "Oskar", "Oskar", "USD")]
    }
}
