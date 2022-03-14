package pl.umcs.shop_app.base.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public class Auditable extends BaseEntity {

    public LocalDateTime createdDate;

    public LocalDateTime lastModifiedDate;

    @PrePersist
    private void prePersist() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.lastModifiedDate = LocalDateTime.now();
    }
}
