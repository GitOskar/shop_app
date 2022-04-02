package pl.umcs.shop_app.domain.order.service.implementation;

import com.neovisionaries.i18n.CurrencyCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.umcs.shop_app.domain.currencyexchanger.CurrencyExchanger;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.domain.order.dto.*;
import pl.umcs.shop_app.domain.order.entity.OrderPart;
import pl.umcs.shop_app.domain.order.entity.UserOrder;
import pl.umcs.shop_app.domain.order.enumerate.OrderStatus;
import pl.umcs.shop_app.domain.order.repository.OrderQueryDslRepository;
import pl.umcs.shop_app.domain.order.repository.OrderRepository;
import pl.umcs.shop_app.domain.order.service.OrderService;
import pl.umcs.shop_app.domain.payu.payment.service.PayuPaymentService;
import pl.umcs.shop_app.domain.product.entity.Product;
import pl.umcs.shop_app.domain.product.repository.ProductRepository;
import pl.umcs.shop_app.security.entity.AppUser;
import pl.umcs.shop_app.security.repository.AppUserRepository;
import pl.umcs.shop_app.util.MoneyUtil;
import pl.umcs.shop_app.util.SecurityUtil;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toMap;
import static pl.umcs.shop_app.domain.exception.ErrorStatus.*;
import static pl.umcs.shop_app.domain.order.mapper.OrderMapper.toDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CurrencyExchanger currencyExchanger;
    private final AppUserRepository appUserRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final PayuPaymentService paymentService;
    private final OrderQueryDslRepository orderQueryDslRepository;


    @Override
    @Transactional
    public Mono<MakeOrderResponseDto> makeOrder(MakeOrderRequest request) {

        String username = SecurityUtil.getUsername();
        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(USER_NOT_FOUND_EXCEPTION));

        List<Long> productIds = request.getProducts().stream()
                .map(MakeOrderProduct::getId).collect(toCollection(ArrayList::new));

        List<Product> products = productRepository.findAllById(productIds);

        Map<Long, Product> productsMap = products.stream().collect(toMap(Product::getId, p -> p));

        validateProducts(productsMap, request);

        UserOrder order = new UserOrder(null, OrderStatus.ERROR, username, request.getIpAddress(), LocalDateTime.now(), user, null, null);

        List<OrderPart> orderParts = reduceProductQuantityAndPrepareOrderParts(request, productsMap, user.getSettlementCurrency(), order);

        order.setOrderParts(orderParts);

        Money totalPrice = orderParts.stream()
                .map(OrderPart::calculateTotalPrice)
                .reduce(MoneyUtil.toMoney(user.getSettlementCurrency(), ZERO), Money::plus);

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        return paymentService.sendTransaction(order)
                .doOnNext(r -> orderRepository.save(order))
                .map(response -> new MakeOrderResponseDto(toDto(order), response.getRedirectUri(), response.getStatus().getStatusCode()));
    }

    @Override
    public OrderDto findByUsernameAndOrderId(String username, Long orderId) {
        UserOrder order = orderRepository.findByIdAndOrderedBy(orderId, username);
        return toDto(order);
    }

    @Override
    public Page<OrderShortDto> searchOrders(OrderFilter orderFilter, Pageable pageable) {
        return orderQueryDslRepository.findProducts(orderFilter, pageable);
    }

    private List<OrderPart> reduceProductQuantityAndPrepareOrderParts(MakeOrderRequest request, Map<Long, Product> productsMap, CurrencyCode settlementCurrency, UserOrder order) {
        return request.getProducts().stream()
                .map(p -> {
                    Product product = productsMap.get(p.getId());
                    product.reduceQuantity(p.getQuantity());
                    Money unitPrice = currencyExchanger.exchange(product.getPrice(), settlementCurrency);
                    return new OrderPart(p.getQuantity(), unitPrice, order, product);
                })
                .collect(toCollection(ArrayList::new));
    }

    private void validateProducts(Map<Long, Product> orderedProducts, MakeOrderRequest request) {

        if (request.getProducts().size() != orderedProducts.size()) {
            log.error("Didnt find products. Request: {}, DB: {}", request.getProducts(), orderedProducts.values());
            throw new ApiException(PRODUCTS_NOT_FOUND);
        }

        request.getProducts()
                .forEach(p -> {
                    Product product = orderedProducts.get(p.getId());
                    if (p.getQuantity().compareTo(product.getQuantityAvailable()) > 0) {
                        throw new ApiException(ORDER_EXCEEDS_AVAILABLE_PRODUCT_NUMBER);
                    }
                });
    }
}
