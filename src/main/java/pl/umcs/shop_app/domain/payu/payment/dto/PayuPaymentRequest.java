package pl.umcs.shop_app.domain.payu.payment.dto;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.umcs.shop_app.domain.order.entity.OrderPart;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.security.entity.AppUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toCollection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayuPaymentRequest {

    private String customerIp;
    private String merchantPosId;
    private String description;
    private CurrencyCode currencyCode;
    private String totalAmount;
    private PayuBuyer buyer;
    private List<PayuProduct> products;

    public static PayuPaymentRequest from(UserOrder order, String merchantPosId) {

        AppUser user = order.getUser();

        return new PayuPaymentRequest(
                order.getCustomerIp(),
                merchantPosId,
                "Order number " + order.getId(),
                user.getSettlementCurrency(),
                Long.toString(order.getTotalPrice().getAmountMinorLong()),
                prepareBuyer(order),
                prepareProducts(order.getOrderParts())
        );
    }

    public static List<PayuProduct> prepareProducts(Collection<OrderPart> orderParts) {

        return orderParts.stream()
                .map(part -> new PayuProduct(part.getProduct().getName(),
                        Long.toString(part.getUnitPrice().getAmountMinorLong()),
                        Long.toString(part.getQuantity().longValue())))
                .collect(toCollection(ArrayList::new));
    }

    public static PayuBuyer prepareBuyer(UserOrder order) {

        AppUser user = order.getUser();

        return new PayuBuyer(user.getUsername(), user.getPhoneNumber(), user.getFirstName(), user.getLastName());
    }
}
