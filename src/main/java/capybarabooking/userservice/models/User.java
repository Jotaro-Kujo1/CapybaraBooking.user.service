package capybarabooking.userservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import static capybarabooking.userservice.constants.ErrorMessages.INVALID_EMAIL_MESSAGE;
import static capybarabooking.userservice.constants.ErrorMessages.INVALID_PHONE_NUMBER_MESSAGE;

@Entity
@Table(name = "users", uniqueConstraints =
        {
                @UniqueConstraint(columnNames = "login")
        })
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    public String id;

    @NonNull
    public String login;

    @NonNull
    public String password;

    @Pattern(regexp = "\\+7[0-9]{10}", message = INVALID_PHONE_NUMBER_MESSAGE)
    public String phone;

    @Email(message = INVALID_EMAIL_MESSAGE)
    public String email;

    public String post;
}
