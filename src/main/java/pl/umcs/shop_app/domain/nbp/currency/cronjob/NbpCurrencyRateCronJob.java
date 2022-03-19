package pl.umcs.shop_app.domain.nbp.currency.cronjob;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.umcs.shop_app.domain.nbp.currency.service.NbpCurrencyRateService;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class NbpCurrencyRateCronJob {

    private final NbpCurrencyRateService currencyRateService;

    @Scheduled(cron = "${config.cron.currency-rate}")
    public void nbpCurrencyRateCronJob() {
        log.info("[START] NBP currency rate cron job");
        currencyRateService.downloadCurrencyRates(LocalDate.now().minusDays(1))
                .subscribe();
    }
}
