package pl.umcs.shop_app.base.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import static java.util.Objects.isNull;

@Getter
@MappedSuperclass
@EqualsAndHashCode
public class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    public boolean isNew() {
        return isNull(id);
    }
}
