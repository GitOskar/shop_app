package pl.umcs.shop_app.security.mapper

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

        where:
        request << [new RegisterRequest("user@domain.pl", "password"), new RegisterRequest("user23@domain11.com.pl", "zaq1@WSX")]
    }
}
