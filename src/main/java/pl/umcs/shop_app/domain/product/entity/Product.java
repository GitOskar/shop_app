package pl.umcs.shop_app.domain.product.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import pl.umcs.shop_app.base.entity.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends Auditable {

    @Column(unique = true)
    private String name;

    @Columns(columns = {@Column(name = "price_currency"), @Column(name = "price_amount")})
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money price;

    private BigDecimal quantityAvailable;

    public void reduceQuantity(BigDecimal number) {
        quantityAvailable = quantityAvailable.subtract(number);
    }
}
