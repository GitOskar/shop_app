package pl.umcs.shop_app.domain.product.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateProductDto extends BaseProductRequestDto {

    @Min(1)
    @NotNull
    Long id;
}
