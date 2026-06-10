package rafaelandrade.ipurchases.orders.exception;

import lombok.Getter;

@Getter
public class ValidateException extends RuntimeException {

    private String field;
    private String message;

    public ValidateException(String field, String message) {
        super(message);
        this.field = field;
        this.message = message;
    }
}
