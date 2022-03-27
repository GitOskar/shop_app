package pl.umcs.shop_app.security.entity;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.umcs.shop_app.base.entity.Auditable;
import pl.umcs.shop_app.domain.order.entity.UserOrder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppUser extends Auditable {

    @Column(unique = true)
    private String username;

    @ToString.Exclude
    private String passwordHash;

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private CurrencyCode settlementCurrency;

    @ToString.Exclude
    @OneToMany(fetch = LAZY, cascade = ALL, mappedBy = "user")
    private List<UserOrder> orders = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<Role> roles = new HashSet<>();

    public AppUser(String username, String passwordHash, String phoneNumber, String firstName, String lastName, CurrencyCode settlementCurrency) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.settlementCurrency = settlementCurrency;
    }
}
