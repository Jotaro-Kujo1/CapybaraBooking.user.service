package capybarabooking.userservice.repositories;

import capybarabooking.userservice.models.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, String> {

    User findAllByLogin(String login);

    @Modifying
    @Query(value = "UPDATE User u SET u.phone = :phone where u.login = :login")
    void updateUserPhoneNumber(String login, String phone);

    @Modifying
    @Query(value = "UPDATE User u SET u.email = :email where u.login = :login")
    void updateUserEmail(String login, String email);
}
