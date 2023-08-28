package capybarabooking.userservice.convertors;

import com.flat_review.openapi.model.UserView;
import capybarabooking.userservice.exceptions.UserException;
import capybarabooking.userservice.models.User;
import lombok.SneakyThrows;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static capybarabooking.userservice.constants.ErrorCodes.NON_NULL_FIELD_IS_NULL;
import static capybarabooking.userservice.constants.ErrorMessages.LOGIN_OR_PASSWORD_IS_NULL_MESSAGE;

@Component
public class UserConverter implements Converter<UserView, User> {

    @SneakyThrows
    @Override
    public User convert(UserView user) {
        User userConvert = new User();
        if (isLoginExists(user)) {
            userConvert.login = (user.getLogin());
        } else {
            throw new UserException(NON_NULL_FIELD_IS_NULL, LOGIN_OR_PASSWORD_IS_NULL_MESSAGE, HttpStatus.NOT_FOUND);
        }
        if (isPasswordExists(user)) {
            userConvert.password = (user.getPassword());
        } else {
            throw new UserException(NON_NULL_FIELD_IS_NULL, LOGIN_OR_PASSWORD_IS_NULL_MESSAGE, HttpStatus.NOT_FOUND);
        }
        return userConvert;
    }

    private boolean isLoginExists(UserView user) {
        return  (Objects.nonNull(user.getLogin()) && !user.getLogin().isBlank() && !user.getLogin().isEmpty());
    }

    private boolean isPasswordExists(UserView user) {
        return  (Objects.nonNull(user.getPassword()) && !user.getPassword().isBlank() && !user.getPassword().isEmpty());
    }
}
