package pl.umcs.shop_app.domain.nbp.currency.entity;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.umcs.shop_app.base.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class NbpCurrencyRate extends BaseEntity {

    private LocalDate effectiveDate;

    @Enumerated(STRING)
    private CurrencyCode currency;

    @Column(precision = 13, scale = 7)
    private BigDecimal rate;
}
