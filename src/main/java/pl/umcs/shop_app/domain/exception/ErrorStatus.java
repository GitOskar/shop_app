package pl.umcs.shop_app.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@ToString
@RequiredArgsConstructor
public enum ErrorStatus {

    // 404
    USER_NOT_FOUND_EXCEPTION("User not found", NOT_FOUND),
    ERROR_OCCURRED_DURING_TOKEN_VERIFYING("Error occurred during token verifying", NOT_FOUND),
    PRODUCTS_NOT_FOUND("Products not found", NOT_FOUND),
    ORDER_NOT_FOUND("Order not found", NOT_FOUND),

    // 409
    USER_ALREADY_EXISTS("User already exists", CONFLICT),
    PRODUCT_ALREADY_EXISTS("Product already exists", CONFLICT),

    // 422
    ORDER_EXCEEDS_AVAILABLE_PRODUCT_NUMBER("Order exceeds available product number", UNPROCESSABLE_ENTITY),

    // 500
    ERROR_DURING_CURRENCY_EXCHANGING("Error during currency exchanging", INTERNAL_SERVER_ERROR),
    ERROR_PAYMENTS_CONNECTION("Error with payments connection", INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;
}
