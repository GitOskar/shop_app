package pl.umcs.shop_app.domain.order.entity

import org.joda.money.CurrencyUnit
import org.joda.money.Money
import pl.umcs.shop_app.domain.product.entity.Product
import spock.lang.Specification

class OrderPartTest extends Specification {

    def "should calculate total price correctly"() {

        given:
        OrderPart orderPart = new OrderPart(quantity: quantity, product: new Product(price: productPrice))

        when:
        orderPart.calculateTotalPrice()

        then:
        orderPart.getPartPrice() == expectedTotalPrice

        where:
        quantity                | productPrice                                          || expectedTotalPrice
        BigDecimal.ONE          | Money.of(CurrencyUnit.USD, BigDecimal.TEN)            || Money.of(CurrencyUnit.USD, BigDecimal.TEN)
        BigDecimal.TEN          | Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(52.21)) || Money.of(CurrencyUnit.EUR, BigDecimal.valueOf(522.10))
        BigDecimal.valueOf(21)  | Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(61.59)) || Money.of(CurrencyUnit.GBP, BigDecimal.valueOf(1293.39))
        BigDecimal.valueOf(1.5) | Money.of(CurrencyUnit.CHF, BigDecimal.valueOf(24.31)) || Money.of(CurrencyUnit.CHF, BigDecimal.valueOf(36.47))
    }
}
