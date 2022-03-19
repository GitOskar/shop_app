package pl.umcs.shop_app.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.umcs.shop_app.domain.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
