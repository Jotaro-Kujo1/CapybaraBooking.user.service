package capybarabooking.userservice.validators;

import com.flat_review.openapi.model.UserView;
import capybarabooking.userservice.exceptions.UserException;
import capybarabooking.userservice.models.User;
import capybarabooking.userservice.repositories.UserRepository;
import capybarabooking.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

import static capybarabooking.userservice.constants.ErrorCodes.*;
import static capybarabooking.userservice.constants.ErrorMessages.*;

@Component
public class UserRequestValidator {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public void isDataCorrectForSignIn(UserView userView) throws UserException {
        if (!isLoginExists(userView.getLogin()) || !isSourceExists(userView.getPassword())) {
            throw new UserException(NON_NULL_FIELD_IS_NULL, LOGIN_OR_PASSWORD_IS_NULL_MESSAGE, HttpStatus.IM_USED);
        }
        User user = userRepository.findAllByLogin(userView.getLogin());
        if (Objects.isNull(user)) {
            throw new UserException(ENTITY_NOT_FOUND, USER_NOT_FOUND_MESSAGE, HttpStatus.FORBIDDEN);
        }
        String hashPass = userService.hashPasswordToSha256(userView.getPassword());
        if (!(user.password.equals(hashPass))) {
            throw new UserException(LOGIN_OR_PASSWORD_INCORRECT, LOGIN_OR_PASSWORD_INCORRECT_MESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    public void isLoginAndPasswordExists(UserView userView) throws UserException {
        if (!isLoginExists(userView.getLogin()) || !isSourceExists(userView.getPassword())) {
            throw new UserException(NON_NULL_FIELD_IS_NULL, LOGIN_OR_PASSWORD_IS_NULL_MESSAGE, HttpStatus.IM_USED);
        }
        if (isLoginNonUnique(userView.getLogin())) {
            throw new UserException(NON_UNIQUE_LOGIN, NON_UNIQUE_LOGIN_MESSAGE, HttpStatus.IM_USED);
        }
    }

    public void isUpdateValid(String login, String source, String pattern, String errorMessage) {
        if (!isLoginExists(login)) {
            throw new UserException(NON_NULL_FIELD_IS_NULL, LOGIN_OR_PASSWORD_IS_NULL_MESSAGE, HttpStatus.IM_USED);
        }
        if (!isSourceExists(source)) {
            throw new UserException(NON_NULL_FIELD_IS_NULL, errorMessage, HttpStatus.IM_USED);
        }
        if (!checkIsPhoneEmailCorrect(pattern, source)) {
            throw new UserException(INVALID_PHONE_NUMBER_0R_EMAIL_CODE, errorMessage, HttpStatus.IM_USED);
        }
    }

    private boolean checkIsPhoneEmailCorrect(String pattern, String source) {
        return Pattern.compile(pattern)
                .matcher(source)
                .matches();
    }

    public boolean isLoginExists(String login) {
        return Objects.nonNull(login) && !login.isBlank() && !login.isEmpty();
    }

    private boolean isSourceExists(String source) {
        return Objects.nonNull(source) && !source.isBlank() && !source.isEmpty();
    }

    private boolean isLoginNonUnique(String login) {
        User user = userRepository.findAllByLogin(login);
        return Objects.nonNull(user);
    }
}
