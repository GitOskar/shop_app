package pl.umcs.shop_app.domain.nbp.currency.repository;

import com.neovisionaries.i18n.CurrencyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.shop_app.domain.nbp.currency.entity.NbpCurrencyRate;

import java.util.Optional;

@Repository
public interface NbpCurrencyRateRepository extends JpaRepository<NbpCurrencyRate, Long> {

    Optional<NbpCurrencyRate> findTopByCurrencyOrderByEffectiveDateDesc(CurrencyCode currency);
}
