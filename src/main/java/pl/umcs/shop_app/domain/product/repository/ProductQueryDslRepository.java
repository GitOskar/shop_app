package pl.umcs.shop_app.domain.product.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pl.umcs.shop_app.base.querydsl.BaseQueryDslRepository;
import pl.umcs.shop_app.domain.product.dto.ProductFilter;
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto;
import pl.umcs.shop_app.domain.product.dto.QProductResponseDto;
import pl.umcs.shop_app.domain.product.entity.Product;
import pl.umcs.shop_app.domain.product.entity.QProduct;

import java.util.Optional;

@Repository
public class ProductQueryDslRepository extends BaseQueryDslRepository {

    public ProductQueryDslRepository() {
        super(Product.class);
    }

    public Page<ProductResponseDto> findProducts(ProductFilter filter, Pageable pageable) {

        QProduct qProduct = QProduct.product;

        JPQLQuery<ProductResponseDto> query = from(qProduct)
                .select(new QProductResponseDto(qProduct.id, qProduct.name, qProduct.price, qProduct.quantityAvailable))
                .where(prepareBooleanBuilder(filter, qProduct));

        return applyPagination(pageable, query);
    }

    private BooleanBuilder prepareBooleanBuilder(ProductFilter filter, QProduct qProduct) {

        BooleanBuilder predicate = new BooleanBuilder();

        Optional.ofNullable(filter.getName())
                .map(qProduct.name::startsWith)
                .ifPresent(predicate::and);

        return predicate;
    }
}
