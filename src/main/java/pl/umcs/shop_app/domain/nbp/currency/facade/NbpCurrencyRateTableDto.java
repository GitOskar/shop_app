package pl.umcs.shop_app.domain.nbp.currency.facade;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
class NbpCurrencyRateTableDto {

    private LocalDate effectiveDate;

    private List<NbpSingleCurrencyRateDto> rates;
}
