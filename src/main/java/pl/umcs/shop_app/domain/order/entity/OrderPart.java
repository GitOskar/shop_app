package pl.umcs.shop_app.domain.order.entity;

import lombok.*;
import org.joda.money.Money;
import pl.umcs.shop_app.base.entity.BaseEntity;
import pl.umcs.shop_app.domain.product.entity.Product;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderPart extends BaseEntity {

    private BigDecimal quantity;

    @ToString.Exclude
    @JoinColumn(name = "user_order_id")
    @ManyToOne
    private UserOrder userOrder;

    @ToString.Exclude
    @JoinColumn(name = "product_id")
    @ManyToOne
    private Product product;

    public Money getProductPrice() {
        return product.getPrice();
    }
}
