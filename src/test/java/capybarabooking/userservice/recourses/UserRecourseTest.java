package capybarabooking.userservice.recourses;

import capybarabooking.userservice.convertors.UserConverter;
import capybarabooking.userservice.convertors.UserUpdateEmailConverter;
import capybarabooking.userservice.convertors.UserUpdatePhoneConverter;
import capybarabooking.userservice.convertors.UserUpdatePostConverter;
import capybarabooking.userservice.models.User;
import capybarabooking.userservice.services.UserService;
import capybarabooking.userservice.validators.UserRequestValidator;
import com.capybarabooking.userservice.openapi.model.UserView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
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

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
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

    @Test
     public void saveUser() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        User user = new User("login", "password");
        user.id = "1";

        when(userService.saveUser(any(User.class))).thenReturn(user);
        when(userConverter.convert(any(UserView.class))).thenReturn(user);

        UserView userView = new UserView();
        userView.setLogin("login");
        userView.setPassword("password");

        mockMvc.perform(post("/user-service/v1/users/saveUser")
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(userView))
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
