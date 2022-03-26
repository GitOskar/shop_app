package pl.umcs.shop_app.domain.product.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AddProductDto extends BaseProductRequestDto {
}
