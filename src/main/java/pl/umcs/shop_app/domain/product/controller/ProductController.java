package pl.umcs.shop_app.domain.product.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.umcs.shop_app.domain.product.dto.AddProductDto;
import pl.umcs.shop_app.domain.product.dto.ProductFilter;
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto;
import pl.umcs.shop_app.domain.product.dto.UpdateProductDto;
import pl.umcs.shop_app.domain.product.entity.Product;
import pl.umcs.shop_app.domain.product.service.ProductService;

import javax.validation.Valid;

import static org.springframework.data.domain.Sort.Direction.ASC;
import static pl.umcs.shop_app.domain.product.mapper.ProductMapper.mapToResponseDto;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/employee/product")
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody AddProductDto dto) {
        log.info("Add product: {}", dto);
        Product product = productService.addProduct(dto);
        return ResponseEntity.ok(mapToResponseDto(product));
    }

    @PutMapping("/employee/product")
    public ResponseEntity<Object> updateProduct(@Valid @RequestBody UpdateProductDto dto) {
        log.info("Update product. Request: {}", dto);
        productService.updateProduct(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/public/product/search")
    public Page<ProductResponseDto> searchUserPage(@PageableDefault(sort = "id", direction = ASC) Pageable pageable,
                                                   @RequestBody ProductFilter filter) {

        log.info("Search products with filter: {}", filter);
        return productService.findProducts(filter, pageable);
    }
}