package pl.umcs.shop_app.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.umcs.shop_app.domain.validation.IpAddress;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeOrderRequest {

    @NotNull
    @IpAddress
    private String ipAddress;

    @NotNull
    @NotEmpty
    private List<MakeOrderProduct> products;
}
