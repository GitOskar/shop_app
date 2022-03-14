package pl.umcs.shop_app.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@ToString
@RequiredArgsConstructor
public enum ErrorStatus {

    // User
    USER_NOT_FOUND_EXCEPTION("User not found", NOT_FOUND),

    // Token
    ERROR_OCCURRED_DURING_TOKEN_VERIFYING("Error occurred during token verifying", NOT_FOUND);

    private final String message;
    private final HttpStatus httpStatus;
}
