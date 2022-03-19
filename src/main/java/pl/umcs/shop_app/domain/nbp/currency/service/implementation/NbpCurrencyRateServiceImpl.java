package pl.umcs.shop_app.domain.nbp.currency.service.implementation;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;
import pl.umcs.shop_app.domain.nbp.currency.facade.NbpCurrencyRateFacade;
import pl.umcs.shop_app.domain.nbp.currency.repository.NbpCurrencyRateRepository;
import pl.umcs.shop_app.domain.nbp.currency.service.NbpCurrencyRateService;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NbpCurrencyRateServiceImpl implements NbpCurrencyRateService {

    private final NbpCurrencyRateFacade currencyRateFacade;
    private final NbpCurrencyRateRepository currencyRateRepository;

    @Override
    public Mono<List<NbpCurrencyRate>> downloadCurrencyRates(LocalDate effectiveDate) {
        return Mono.just(effectiveDate)
                .doOnNext(date -> log.info("Download currency rates for date: {}", effectiveDate))
                .flatMap(currencyRateFacade::downloadCurrencyRates)
                .map(currencyRateRepository::saveAll);
    }

    @PostConstruct
    public void test() {
        System.out.println(currencyRateRepository.findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode.USD).get());
    }
}
