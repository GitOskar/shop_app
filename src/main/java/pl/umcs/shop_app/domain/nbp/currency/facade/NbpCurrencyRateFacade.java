package pl.umcs.shop_app.domain.nbp.currency.facade;

import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;

public interface NbpCurrencyRateFacade {

    Mono<List<NbpCurrencyRate>> downloadCurrencyRates(LocalDate publicationDate);
}
