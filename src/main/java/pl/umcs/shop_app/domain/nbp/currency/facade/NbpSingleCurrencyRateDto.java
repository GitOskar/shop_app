package pl.umcs.shop_app.domain.nbp.currency.facade;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
class NbpSingleCurrencyRateDto {

    private String currency;
    private CurrencyCode code;
    private BigDecimal mid;
}
