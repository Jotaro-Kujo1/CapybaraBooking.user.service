package capybarabooking.userservice.exceptions;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

    public String errorCode;
    public String errorMessage;
    public HttpStatus httpStatus;

    public UserException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
