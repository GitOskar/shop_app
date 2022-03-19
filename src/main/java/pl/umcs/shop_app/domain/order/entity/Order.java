package pl.umcs.shop_app.domain.order.entity;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import pl.umcs.shop_app.base.entity.Auditable;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends Auditable {

    @Columns(columns = {@Column(name = "total_price_currency"), @Column(name = "total_price_amount")})
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money totalPrice;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    private String orderedBy;

    private LocalDateTime orderDate;

    @ToString.Exclude
    @OneToMany(fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();
}
