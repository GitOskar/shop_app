package pl.umcs.shop_app.security.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.umcs.shop_app.base.querydsl.BaseQueryDslRepository;
import pl.umcs.shop_app.security.dto.UserFilter;
import pl.umcs.shop_app.security.entity.AppUser;
import pl.umcs.shop_app.security.entity.QAppUser;

import java.util.Optional;

@Repository
public class AppUserQueryDslRepository extends BaseQueryDslRepository {


    public AppUserQueryDslRepository() {
        super(AppUser.class);
    }

    public Page<AppUser> findUsers(UserFilter filter, Pageable pageable) {

        QAppUser qAppUser = QAppUser.appUser;

        JPQLQuery<AppUser> query = from(qAppUser).where(prepareBooleanBuilder(filter, qAppUser));

        return applyPagination(pageable, query);
    }

    private BooleanBuilder prepareBooleanBuilder(UserFilter filter, QAppUser qAppUser) {

        BooleanBuilder predicate = new BooleanBuilder();

        Optional.ofNullable(filter.getUsername())
                .map(qAppUser.username::startsWith)
                .ifPresent(predicate::and);

        Optional.ofNullable(filter.getCreatedDateFrom())
                .map(qAppUser.createdDate::after)
                .ifPresent(predicate::and);

        Optional.ofNullable(filter.getCreatedDateTo())
                .map(qAppUser.createdDate::before)
                .ifPresent(predicate::and);

        return predicate;
    }
}
