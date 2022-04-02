package pl.umcs.shop_app.domain.order.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.umcs.shop_app.base.querydsl.BaseQueryDslRepository;
import pl.umcs.shop_app.domain.order.dto.OrderFilter;
import pl.umcs.shop_app.domain.order.dto.OrderShortDto;
import pl.umcs.shop_app.domain.order.dto.QOrderShortDto;
import pl.umcs.shop_app.domain.order.entity.QUserOrder;
import pl.umcs.shop_app.domain.order.entity.UserOrder;

import java.util.Optional;

@Repository
public class OrderQueryDslRepository extends BaseQueryDslRepository {

    public OrderQueryDslRepository() {
        super(UserOrder.class);
    }

    public Page<OrderShortDto> findProducts(OrderFilter filter, Pageable pageable) {

        QUserOrder qOrder = QUserOrder.userOrder;

        JPQLQuery<OrderShortDto> query = from(qOrder)
                .select(new QOrderShortDto(qOrder.id, qOrder.totalPrice, qOrder.orderStatus))
                .where(prepareBooleanBuilder(filter, qOrder));

        return applyPagination(pageable, query);
    }

    private BooleanBuilder prepareBooleanBuilder(OrderFilter filter, QUserOrder qOrder) {

        BooleanBuilder predicate = new BooleanBuilder();

        Optional.ofNullable(filter.getUsername())
                .map(qOrder.orderedBy::eq)
                .ifPresent(predicate::and);

        return predicate;
    }
}
