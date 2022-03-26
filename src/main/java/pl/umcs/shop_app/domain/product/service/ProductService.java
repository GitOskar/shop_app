package pl.umcs.shop_app.domain.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.umcs.shop_app.domain.product.dto.AddProductDto;
import pl.umcs.shop_app.domain.product.dto.ProductFilter;
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto;
import pl.umcs.shop_app.domain.product.dto.UpdateProductDto;
import pl.umcs.shop_app.domain.product.entity.Product;

public interface ProductService {

    Product addProduct(AddProductDto addProductDto);

    void updateProduct(UpdateProductDto updateProductDto);

    Page<ProductResponseDto> findProducts(ProductFilter filter, Pageable pageable);
}
