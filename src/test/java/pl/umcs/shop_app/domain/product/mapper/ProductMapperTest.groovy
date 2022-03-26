package pl.umcs.shop_app.domain.product.mapper

import com.neovisionaries.i18n.CurrencyCode
import org.joda.money.CurrencyUnit
import org.joda.money.Money
import pl.umcs.shop_app.domain.product.dto.AddProductDto
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto
import pl.umcs.shop_app.domain.product.entity.Product
import spock.lang.Specification

class ProductMapperTest extends Specification {

    def "should correctly map to dto"() {

        expect:
        ProductMapper.mapToResponseDto(entity) == expectedDto

        where:
        entity                                                                                                                      || expectedDto
        null                                                                                                                        || null
        new Product(name: "Apple", price: Money.of(CurrencyUnit.USD, BigDecimal.TEN), quantityAvailable: BigDecimal.ONE)            || new ProductResponseDto(name: "Apple", amount: BigDecimal.valueOf(1000, 2), currency: CurrencyCode.USD, quantityAvailable: BigDecimal.ONE)
        new Product(name: "Pear", price: Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(11.2)), quantityAvailable: BigDecimal.TEN)   || new ProductResponseDto(name: "Pear", amount: BigDecimal.valueOf(1120, 2), currency: CurrencyCode.EUR, quantityAvailable: BigDecimal.TEN)
        new Product(name: "Banana", price: Money.of(CurrencyUnit.JPY, BigDecimal.valueOf(666)), quantityAvailable: BigDecimal.ZERO) || new ProductResponseDto(name: "Banana", amount: BigDecimal.valueOf(666), currency: CurrencyCode.JPY, quantityAvailable: BigDecimal.ZERO)
    }

    def "should map to entity correctly"() {
        expect:
        ProductMapper.mapToEntity(dto) == expectedEntity

        where:
        dto                                                                                                                               || expectedEntity
        null                                                                                                                              || null
        new AddProductDto(name: "Apple", amount: BigDecimal.ONE, currency: "USD", quantityAvailable: BigDecimal.TEN)                      || new Product(name: "Apple", price: Money.of(CurrencyUnit.USD, BigDecimal.ONE), quantityAvailable: BigDecimal.TEN)
        new AddProductDto(name: "Pear", amount: BigDecimal.valueOf(54.2), currency: "USD", quantityAvailable: BigDecimal.ONE)             || new Product(name: "Pear", price: Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.2)), quantityAvailable: BigDecimal.ONE)
        new AddProductDto(name: "Banana", amount: BigDecimal.valueOf(22.32), currency: "USD", quantityAvailable: BigDecimal.ZERO)         || new Product(name: "Banana", price: Money.of(CurrencyUnit.USD, BigDecimal.valueOf(22.32)), quantityAvailable: BigDecimal.ZERO)
        new AddProductDto(name: "Grape", amount: BigDecimal.valueOf(54.1), currency: "USD", quantityAvailable: BigDecimal.valueOf(66.21)) || new Product(name: "Grape", price: Money.of(CurrencyUnit.USD, BigDecimal.valueOf(54.1)), quantityAvailable: BigDecimal.valueOf(66.21))
    }
}
