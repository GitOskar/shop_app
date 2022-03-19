package pl.umcs.shop_app.domain.nbp.currency.emergency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
class DownloadNbpCurrencyRateRequest {

    @NotNull
    private LocalDate effectiveDate;
}
