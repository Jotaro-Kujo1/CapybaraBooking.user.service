package capybarabooking.userservice.convertors;

import com.flat_review.openapi.model.UserUpdateEmail;
import capybarabooking.userservice.models.User;
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
