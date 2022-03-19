package pl.umcs.shop_app.security.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.umcs.shop_app.base.entity.Auditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppUser extends Auditable {

    @Column(unique = true)
    private String username;

    @ToString.Exclude
    private String passwordHash;

    @ManyToMany(fetch = EAGER, cascade = ALL)
    private Set<Role> roles = new HashSet<>();

    public AppUser(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
    }
}
