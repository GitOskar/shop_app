package pl.umcs.shop_app.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.umcs.shop_app.domain.order.entity.UserOrder;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
}
