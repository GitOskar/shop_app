package pl.umcs.shop_app.domain.product.service.implementation;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.RequiredArgsConstructor;
import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.domain.product.dto.AddProductDto;
import pl.umcs.shop_app.domain.product.dto.ProductFilter;
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto;
import pl.umcs.shop_app.domain.product.dto.UpdateProductDto;
import pl.umcs.shop_app.domain.product.entity.Product;
import pl.umcs.shop_app.domain.product.mapper.ProductMapper;
import pl.umcs.shop_app.domain.product.repository.ProductQueryDslRepository;
import pl.umcs.shop_app.domain.product.repository.ProductRepository;
import pl.umcs.shop_app.domain.product.service.ProductService;
import pl.umcs.shop_app.util.MoneyUtil;

import static pl.umcs.shop_app.domain.exception.ErrorStatus.PRODUCT_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryDslRepository productQueryDslRepository;

    @Override
    public Product addProduct(AddProductDto dto) {

        if (productRepository.existsByName(dto.getName())) {
            throw new ApiException(PRODUCT_ALREADY_EXISTS);
        }

        Product entity = ProductMapper.mapToEntity(dto);
        return productRepository.save(entity);
    }

    @Override
    public void updateProduct(UpdateProductDto dto) {

        CurrencyCode currency = CurrencyCode.valueOf(dto.getCurrency());
        Money price = MoneyUtil.toMoney(currency, dto.getAmount());

        productRepository.updateById(dto.getId(), dto.getName(), price, dto.getQuantityAvailable());
    }

    @Override
    public Page<ProductResponseDto> findProducts(ProductFilter filter, Pageable pageable) {
        return productQueryDslRepository.findProducts(filter, pageable);
    }
}
