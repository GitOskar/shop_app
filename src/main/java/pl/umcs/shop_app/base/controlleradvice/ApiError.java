package pl.umcs.shop_app.base.controlleradvice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private String message;
    private String httpStatusCode;
    private String httpStatusMessage;
}
