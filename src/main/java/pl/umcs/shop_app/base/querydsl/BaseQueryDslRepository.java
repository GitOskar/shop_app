package pl.umcs.shop_app.base.querydsl;

import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public abstract class BaseQueryDslRepository extends QuerydslRepositorySupport {

    protected BaseQueryDslRepository(Class<?> domainClass) {
        super(domainClass);
    }

    protected <T> PageImpl<T> applyPagination(Pageable pageable, JPQLQuery<T> query) {
        long count = query.fetchCount();
        List<T> data = getQuerydsl().applyPagination(pageable, query).fetch();

        return new PageImpl<>(data, pageable, count);
    }
}
