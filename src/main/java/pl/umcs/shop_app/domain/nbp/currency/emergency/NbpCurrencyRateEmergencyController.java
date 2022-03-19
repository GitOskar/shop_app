package pl.umcs.shop_app.domain.nbp.currency.emergency;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.shop_app.domain.nbp.currency.service.NbpCurrencyRateService;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/emergency/nbp")
class NbpCurrencyRateEmergencyController {

    private final NbpCurrencyRateService currencyRateService;

    @PostMapping("/download-currency-rates")
    public Mono<ResponseEntity<Object>> emergencyDownloadCurrencyRate(@Valid @RequestBody DownloadNbpCurrencyRateRequest request) {

        return Mono.just(request)
                .doOnNext(req -> log.info("Download nbp currency rates emergency request {}", req))
                .map(DownloadNbpCurrencyRateRequest::getEffectiveDate)
                .flatMap(currencyRateService::downloadCurrencyRates)
                .map(list -> ResponseEntity.noContent().build())
                .onErrorResume(throwable -> Mono.just(ResponseEntity.internalServerError().body(throwable.getMessage())));
    }
}
