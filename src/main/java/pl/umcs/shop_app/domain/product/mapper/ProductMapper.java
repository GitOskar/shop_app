package pl.umcs.shop_app.domain.product.mapper;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.NoArgsConstructor;
import org.joda.money.Money;
import pl.umcs.shop_app.domain.product.dto.AddProductDto;
import pl.umcs.shop_app.domain.product.dto.BaseProductRequestDto;
import pl.umcs.shop_app.domain.product.dto.ProductResponseDto;
import pl.umcs.shop_app.domain.product.entity.Product;
import pl.umcs.shop_app.util.MoneyUtil;

import static java.util.Objects.isNull;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ProductMapper {

    public static ProductResponseDto mapToResponseDto(Product product) {

        if (isNull(product)) {
            return null;
        }

        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getQuantityAvailable());
    }

    public static Product mapToEntity(AddProductDto dto) {
        Product entity = mapBaseParamsToEntity(dto);
        return entity;
    }

    private static Product mapBaseParamsToEntity(BaseProductRequestDto dto) {

        if (isNull(dto)) {
            return null;
        }

        CurrencyCode currency = CurrencyCode.valueOf(dto.getCurrency());
        Money price = MoneyUtil.toMoney(currency, dto.getAmount());

        return new Product(dto.getName(), price, dto.getQuantityAvailable());
    }
}
