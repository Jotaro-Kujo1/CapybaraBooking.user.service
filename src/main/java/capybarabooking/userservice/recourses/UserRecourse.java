package capybarabooking.userservice.recourses;

import capybarabooking.userservice.convertors.UserUpdatePostConverter;
import capybarabooking.userservice.exceptions.UserException;
import com.flat_review.openapi.model.UserUpdateEmail;
import com.flat_review.openapi.model.UserUpdatePhone;
import com.flat_review.openapi.model.UserUpdatePost;
import com.flat_review.openapi.model.UserView;
import capybarabooking.userservice.convertors.UserConverter;
import capybarabooking.userservice.convertors.UserUpdateEmailConverter;
import capybarabooking.userservice.convertors.UserUpdatePhoneConverter;
import capybarabooking.userservice.models.User;
import capybarabooking.userservice.services.UserService;
import capybarabooking.userservice.validators.UserRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static capybarabooking.userservice.constants.Constants.IS_EMAIL_CORRECT_PATTERN;
import static capybarabooking.userservice.constants.Constants.IS_PHONE_CORRECT_PATTERN;
import static capybarabooking.userservice.constants.ErrorCodes.NON_NULL_FIELD_IS_NULL;
import static capybarabooking.userservice.constants.ErrorMessages.*;
import static capybarabooking.userservice.constants.SuccessfulMessages.USER_CREATED_SUCCESSFUL_MESSAGE;
import static capybarabooking.userservice.constants.SuccessfulMessages.USER_SIGN_IN_SUCCESSFUL_MESSAGE;
import static capybarabooking.userservice.constants.VariableNames.LOGIN;
import static capybarabooking.userservice.constants.VariableNames.MESSAGE;

@RestController
@RequestMapping("/flat-review/v1/users")
public class UserRecourse {

    @Autowired
    private UserService userService;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private UserUpdatePhoneConverter userUpdatePhoneConverter;

    @Autowired
    private UserUpdateEmailConverter userUpdateEmailConverter;

    @Autowired
    private UserUpdatePostConverter userUpdatePostConverter;

    @Autowired
    private UserRequestValidator userRequestValidator;

    @PostMapping(value = "/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserView user) {
        Map<String, String> responseData = new HashMap<>();
        userRequestValidator.isLoginAndPasswordExists(user);
        User userConvert = userConverter.convert(user);
        User newUser = userService.saveUser(userConvert);
        responseData.put("id", newUser.id);
        responseData.put(LOGIN, newUser.login);
        responseData.put(MESSAGE, USER_CREATED_SUCCESSFUL_MESSAGE);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> signIn(@RequestBody UserView user) {
        Map<String, String> responseData = new HashMap<>();
        userRequestValidator.isDataCorrectForSignIn(user);
        responseData.put(LOGIN, user.getLogin());
        responseData.put(MESSAGE, USER_SIGN_IN_SUCCESSFUL_MESSAGE);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/updatePhoneNumber")
    public ResponseEntity<?> updatePhoneNumber(@RequestBody UserUpdatePhone userUpdatePhone) {
        Map<String, String> responseData = new HashMap<>();
        userRequestValidator.isUpdateValid(userUpdatePhone.getLogin(), userUpdatePhone.getPhone(), IS_PHONE_CORRECT_PATTERN, INVALID_PHONE_NUMBER_MESSAGE);
        User user = userUpdatePhoneConverter.convert(userUpdatePhone);
        userService.updatePhoneNumber(user);
        responseData.put(LOGIN, user.login);
        responseData.put(MESSAGE, user.phone);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/updateEmail")
    public ResponseEntity<?> updateEmail(@RequestBody UserUpdateEmail userUpdateEmail) {
        Map<String, String> responseData = new HashMap<>();
        userRequestValidator.isUpdateValid(userUpdateEmail.getLogin(), userUpdateEmail.getEmail(), IS_EMAIL_CORRECT_PATTERN, INVALID_EMAIL_MESSAGE);
        User user = userUpdateEmailConverter.convert(userUpdateEmail);
        userService.updateEmail(user);
        responseData.put(LOGIN, user.login);
        responseData.put(MESSAGE, user.email);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PostMapping(value = "/updatePost")
    public ResponseEntity<?> updatePost(@RequestBody UserUpdatePost userUpdatePost) {
        Map<String, String> responseData = new HashMap<>();
        User user = userUpdatePostConverter.convert(userUpdatePost);
        userService.updatePost(user);
        responseData.put(LOGIN, user.login);
        responseData.put(MESSAGE, user.post);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

}
