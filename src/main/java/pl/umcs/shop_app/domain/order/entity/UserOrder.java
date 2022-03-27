package pl.umcs.shop_app.domain.order.entity;

import lombok.*;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;
import pl.umcs.shop_app.base.entity.Auditable;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;
import pl.umcs.shop_app.security.entity.AppUser;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserOrder extends Auditable {

    @Columns(columns = {@Column(name = "total_price_currency"), @Column(name = "total_price_amount")})
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money totalPrice;

    @Enumerated(STRING)
    private OrderStatus orderStatus;

    private String orderedBy;

    private String customerIp;

    private LocalDateTime orderDate;

    @ToString.Exclude
    @ManyToOne(fetch = LAZY)
    private AppUser user;

    @ToString.Exclude
    @OneToMany(fetch = EAGER, mappedBy = "userOrder", cascade = ALL, orphanRemoval = true)
    private List<OrderPart> orderParts = new ArrayList<>();
}
