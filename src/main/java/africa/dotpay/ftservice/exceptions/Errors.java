package africa.dotpay.ftservice.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Errors {
    private final String statusCode;
    private final String message;
    private Object data;

    Errors(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
