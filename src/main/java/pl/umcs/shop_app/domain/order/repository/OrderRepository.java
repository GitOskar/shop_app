package pl.umcs.shop_app.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {

    @Query("select orderId from UserOrder where orderDate > :to and orderStatus = 'UNPAID'")
    List<String> findAllOrderIdsAfterDateAndStatus(LocalDateTime to);

    @Modifying
    @Transactional
    @Query("update UserOrder set orderStatus = :status where orderId = :orderId")
    void updateStatus(String orderId, OrderStatus status);

    Optional<UserOrder> findByIdAndOrderedBy(Long id, String orderedBy);
}
