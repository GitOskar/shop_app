package pl.umcs.shop_app.domain.nbp.currency.service;

import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface NbpCurrencyRateService {

    Mono<List<NbpCurrencyRate>> downloadCurrencyRates(LocalDate effectiveDate);
}
