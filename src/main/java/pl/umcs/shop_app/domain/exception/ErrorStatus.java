package pl.umcs.shop_app.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@ToString
@RequiredArgsConstructor
public enum ErrorStatus {

    // 404
    USER_NOT_FOUND_EXCEPTION("User not found", NOT_FOUND),
    ERROR_OCCURRED_DURING_TOKEN_VERIFYING("Error occurred during token verifying", NOT_FOUND),

    // 500
    ERROR_DURING_CURRENCY_EXCHANGING("Error during currency exchanging", INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;
}
