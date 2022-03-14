package pl.umcs.shop_app.base.controlleradvice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.umcs.shop_app.domain.exception.ApiException;
import pl.umcs.shop_app.domain.exception.ErrorStatus;

import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@ControllerAdvice
public class BaseControllerAdvice {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiError> handleApiException(ApiException apiException) {

        ErrorStatus errorStatus = apiException.getErrorStatus();
        HttpStatus httpStatus = errorStatus.getHttpStatus();
        return new ResponseEntity<>(new ApiError(errorStatus.getMessage(), Integer.toString(httpStatus.value()), httpStatus.name()), httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        Map<String, String> errors = exception.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, DefaultMessageSourceResolvable::getDefaultMessage));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOtherException(Exception exception) {
        log.error("Unexpected exception occurred:", exception);
        return ResponseEntity.internalServerError()
                .body(new ApiError("Internal server error", Integer.toString(INTERNAL_SERVER_ERROR.value()), INTERNAL_SERVER_ERROR.name()));
    }
}
