package pl.umcs.shop_app.domain.nbp.currency.facade;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.time.format.DateTimeFormatter.ISO_DATE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@Slf4j
@Component
@RequiredArgsConstructor
public class NbpCurrencyRateFacadeImpl implements NbpCurrencyRateFacade {

    private final WebClient nbpWebClient;

    @Override
    public Mono<List<NbpCurrencyRate>> downloadCurrencyRates(LocalDate publicationDate) {

        return nbpWebClient.get()
                .uri(ISO_DATE.format(publicationDate))
                .exchangeToMono(this::wrapClientResponse)
                .map(NbpCurrencyRateTableDtoMapper::mapToEntities);
    }

    private Mono<NbpCurrencyRateTableDto> wrapClientResponse(ClientResponse clientResponse) {

        if (Objects.equals(OK, clientResponse.statusCode())) {
            return clientResponse.bodyToFlux(NbpCurrencyRateTableDto.class)
                    .collectList()
                    .map(list -> list.stream().findFirst()
                            .orElseThrow(() -> {
                                log.error("Currency rates not found");
                                return new IllegalStateException("Currency rates not found");
                            }));
        } else if (Objects.equals(NOT_FOUND, clientResponse.statusCode())) {
            log.error("Currency rates not found");
            throw new IllegalStateException("Currency rates not found");
        } else {
            log.error("Internal error during downloading currency rates");
            throw new IllegalStateException("Internal error during downloading currency rates");
        }
    }
}
