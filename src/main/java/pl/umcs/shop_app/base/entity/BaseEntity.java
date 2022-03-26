package pl.umcs.shop_app.base.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static java.util.Objects.isNull;
import static javax.persistence.GenerationType.IDENTITY;

@Getter
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public boolean isNew() {
        return isNull(id);
    }
}
