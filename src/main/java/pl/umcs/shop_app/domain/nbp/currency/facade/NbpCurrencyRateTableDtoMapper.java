package pl.umcs.shop_app.domain.nbp.currency.facade;

import lombok.NoArgsConstructor;
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toCollection;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class NbpCurrencyRateTableDtoMapper {

    public static List<NbpCurrencyRate> mapToEntities(NbpCurrencyRateTableDto tableDto) {

        return tableDto.getRates().stream()
                .map(rate -> new NbpCurrencyRate(tableDto.getEffectiveDate(), rate.getCode(), rate.getMid()))
                .collect(toCollection(ArrayList::new));
    }
}
