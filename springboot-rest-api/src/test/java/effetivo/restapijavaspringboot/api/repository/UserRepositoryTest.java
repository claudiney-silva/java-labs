package effetivo.restapijavaspringboot.api.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.util.UserCreator;

@DataJpaTest
@DisplayName("Tests for User Respository")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save creates User when Successful")
    void save_PersistUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();

        User userSaved = this.userRepository.save(userToBeSaved);

        Assertions.assertThat(userSaved).isNotNull();

        Assertions.assertThat(userSaved.getId()).isNotNull();

        Assertions.assertThat(userSaved.getFirstName()).isEqualTo(userToBeSaved.getFirstName());
    }

    @Test
    @DisplayName("Save updates User when Successful")
    void save_UpdatesUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();

        User userSaved = this.userRepository.save(userToBeSaved);

        userSaved.setFirstName("David");

        User userUpdated = this.userRepository.save(userSaved);

        Assertions.assertThat(userUpdated).isNotNull();

        Assertions.assertThat(userUpdated.getId()).isNotNull();

        Assertions.assertThat(userUpdated.getFirstName()).isEqualTo(userSaved.getFirstName());
    }

    @Test
    @DisplayName("Delete removes User when Successful")
    void delete_RemovesUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();

        User userSaved = this.userRepository.save(userToBeSaved);

        this.userRepository.delete(userSaved);

        Optional<User> userOptional = this.userRepository.findById(userSaved.getId());

        Assertions.assertThat(userOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By First Name returns list of User when Successful")
    void findByFirstName_ReturnsListOfUser_WhenSuccessful() {
        User userToBeSaved = UserCreator.createUserToBeSaved();

        User userSaved = this.userRepository.save(userToBeSaved);

        String firstName = userSaved.getFirstName();

        List<User> users = this.userRepository.findByFirstName(firstName);

        Assertions.assertThat(users).isNotEmpty().contains(userSaved);
    }

    @Test
    @DisplayName("Find By First Name returns empty list when no User is not found")
    void findByFirstName_ReturnsEmptyList_WhenUserIsNotFound() {
        List<User> users = this.userRepository.findByFirstName("Test");

        Assertions.assertThat(users).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when firstName is empty")
    void save_ThrowsConstraintViolationException_WhenFirstNameIsEmpty() {
        User user = new User();
        // Assertions.assertThatThrownBy(() -> this.userRepository.save(user))
        // .isInstanceOf(ConstraintViolationException.class);

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.userRepository.save(user)).withMessageContaining("Campo obrigatório");
    }
}
