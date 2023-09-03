package capybarabooking.userservice.convertors;

;
import capybarabooking.userservice.models.User;
import com.capybarabooking.userservice.openapi.model.UserUpdateEmail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateEmailConverter implements Converter<UserUpdateEmail, User> {

    @Override
    public User convert(UserUpdateEmail userUpdateEmail) {
        User user = new User();
        user.login = userUpdateEmail.getLogin();
        user.email = userUpdateEmail.getEmail();
        return user;
    }
}