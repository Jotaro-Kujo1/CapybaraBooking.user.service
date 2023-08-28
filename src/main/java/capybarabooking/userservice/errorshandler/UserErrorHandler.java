package capybarabooking.userservice.errorshandler;

import capybarabooking.userservice.exceptions.UserException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static capybarabooking.userservice.constants.VariableNames.ERROR_CODE;
import static capybarabooking.userservice.constants.VariableNames.MESSAGE;

@ControllerAdvice
public class UserErrorHandler {

    @ExceptionHandler(UserException.class)
    protected ResponseEntity<?> handleFlatException(UserException ex) {
        Map<String, String> responseData = new HashMap<>();
        responseData.put(ERROR_CODE, ex.errorCode);
        responseData.put(MESSAGE, ex.errorMessage);
        return new ResponseEntity<>(responseData, ex.httpStatus);
    }
}
