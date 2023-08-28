package capybarabooking.userservice.services;

import capybarabooking.userservice.models.User;
import capybarabooking.userservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        String hashPassword = hashPasswordToSha256(user.password);
        user.password = hashPassword;
        return userRepository.save(user);
    }

    public void updatePhoneNumber(User userUpdatePhoneNumber) {
        userRepository.updateUserPhoneNumber(userUpdatePhoneNumber.login, userUpdatePhoneNumber.phone);
    }

    public void updateEmail(User userUpdateEmail) {
        userRepository.updateUserEmail(userUpdateEmail.login, userUpdateEmail.email);
    }

    public String hashPasswordToSha256(String base) {
        try {
            final MessageDigest digset = MessageDigest.getInstance("SHA-256");
            final byte [] hash = digset.digest(base.getBytes("UTF-8"));
            final StringBuffer hexString = new StringBuffer();
            for (byte b: hash) {
                hexString.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
}
