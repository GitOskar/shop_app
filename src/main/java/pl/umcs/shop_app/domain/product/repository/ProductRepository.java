package pl.umcs.shop_app.domain.product.repository;

import org.joda.money.Money;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import pl.umcs.shop_app.domain.product.entity.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Modifying
    @Transactional
    @Query("update Product p " +
            "set p.name = :name, p.price = :price, p.quantityAvailable = :quantityAvailable " +
            "where p.id = :id")
    void updateById(Long id, String name, Money price, BigDecimal quantityAvailable);

    boolean existsByName(String name);
}
