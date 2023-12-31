package capybarabooking.userservice.convertors;

import capybarabooking.userservice.models.User;
import com.capybarabooking.userservice.openapi.model.UserUpdatePhone;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUpdatePhoneConverter implements Converter<UserUpdatePhone, User> {

    @Override
    public User convert(UserUpdatePhone userUpdatePhone) {
        User user = new User();
        user.login = userUpdatePhone.getLogin();
        user.phone = userUpdatePhone.getPhone();
        return user;
    }

}