package pl.umcs.shop_app.domain.order.entity;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import pl.umcs.shop_app.base.entity.BaseEntity;
import pl.umcs.shop_app.domain.product.entity.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

import static java.math.RoundingMode.HALF_UP;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPart extends BaseEntity {

    private BigDecimal quantity;

    @Columns(columns = {@Column(name = "unit_price_currency"), @Column(name = "unit_price_amount")})
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money unitPrice;

    @ToString.Exclude
    @JoinColumn(name = "user_order_id")
    @ManyToOne
    private UserOrder userOrder;

    @ToString.Exclude
    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = EAGER)
    private Product product;

    public Money calculateTotalPrice() {
        return unitPrice.multipliedBy(quantity, HALF_UP);
    }
}
