package capybarabooking.userservice.recourses;

import capybarabooking.userservice.convertors.UserConverter;
import capybarabooking.userservice.convertors.UserUpdateEmailConverter;
import capybarabooking.userservice.convertors.UserUpdatePhoneConverter;
import capybarabooking.userservice.convertors.UserUpdatePostConverter;
import capybarabooking.userservice.models.User;
import capybarabooking.userservice.services.UserService;
import capybarabooking.userservice.validators.UserRequestValidator;
import com.capybarabooking.userservice.openapi.model.UserUpdateEmail;
import com.capybarabooking.userservice.openapi.model.UserUpdatePhone;
import com.capybarabooking.userservice.openapi.model.UserUpdatePost;
import com.capybarabooking.userservice.openapi.model.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@WebMvcTest(UserRecourse.class)
@RunWith(SpringRunner.class)
class UserRecourseTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserConverter userConverter;

    @MockBean
    private UserUpdatePhoneConverter userUpdatePhoneConverter;

    @MockBean
    private UserUpdateEmailConverter userUpdateEmailConverter;

    @MockBean
    private UserUpdatePostConverter userUpdatePostConverter;

    @MockBean
    private UserRequestValidator userRequestValidator;

    @ParameterizedTest
    @CsvSource({
            "login, password, 1",
            "hrapun, 1234, 2"
    })
     public void saveUser_shouldOkState_whenUserIsValid(String login, String password, String id) throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User(login, password);

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userConverter.convert(any(UserView.class))).thenReturn(user);

        UserView userView = new UserView();
        userView.setLogin(login);
        userView.setPassword(password);

        mockMvc.perform(post("/user-service/v1/users/saveUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userView))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "login, +795350928576"
    })
    public void updatePhoneNumber_shouldUpdatePhoneNumber_whenUserExistsAndPhoneIsValid(String login, String phone) throws Exception {
        User user = new User();
        user.login = login;
        user.phone = phone;

        when(userUpdatePhoneConverter.convert(any(UserUpdatePhone.class))).thenReturn(user);

        UserUpdatePhone userUpdatePhone = new UserUpdatePhone();
        userUpdatePhone.setLogin(login);
        userUpdatePhone.setPhone(phone);

        mockMvc.perform(post("/user-service/v1/users/updatePhoneNumber")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userUpdatePhone))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "login, testMail@gmail.com"
    })
    public void updateEmail_shouldUpdateEmail_whenUserExistsAndEmailIsValid(String login, String email) throws Exception {
        User user = new User();
        user.login = login;
        user.email = email;

        when(userUpdateEmailConverter.convert(any(UserUpdateEmail.class))).thenReturn(user);

        UserUpdateEmail userUpdateEmail = new UserUpdateEmail();
        userUpdateEmail.setLogin(login);
        userUpdateEmail.setEmail(email);

        mockMvc.perform(post("/user-service/v1/users/updateEmail")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userUpdateEmail))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @ParameterizedTest
    @CsvSource({
            "login, Developer"
    })
    public void updatePost_shouldUpdatePost_whenUserExistsAndPostIsValid(String login, String post) throws Exception {
        User user = new User();
        user.login = login;
        user.post = post;

        when(userUpdatePostConverter.convert(any(UserUpdatePost.class))).thenReturn(user);

        UserUpdatePost userUpdatePost = new UserUpdatePost();
        userUpdatePost.setLogin(login);
        userUpdatePost.setPost(post);

        mockMvc.perform(post("/user-service/v1/users/updatePost")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userUpdatePost))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    public String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
