package capybarabooking.userservice.convertors;

import capybarabooking.userservice.models.User;
import com.flat_review.openapi.model.UserUpdatePost;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserUpdatePostConverter implements Converter<UserUpdatePost, User> {

    @Override
    public User convert(UserUpdatePost userUpdatePost) {
        User user = new User();
        user.login = userUpdatePost.getLogin();
        user.post = userUpdatePost.getPost();
        return user;
    }
}
