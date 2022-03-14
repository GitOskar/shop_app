package pl.umcs.shop_app.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.umcs.shop_app.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Column(unique = true)
    private String name;
}
