package effetivo.restapijavaspringboot.api.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.exception.BadRequestException;
import effetivo.restapijavaspringboot.api.repository.UserRepository;
import effetivo.restapijavaspringboot.api.util.UserCreator;
import effetivo.restapijavaspringboot.api.util.UserPostRequestBodyCreator;
import effetivo.restapijavaspringboot.api.util.UserPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createValidUser()));
        BDDMockito.when(userRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class))).thenReturn(userPage);

        BDDMockito.when(userRepositoryMock.findAll()).thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.findByFirstName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userRepositoryMock.save(ArgumentMatchers.any(User.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userRepositoryMock).deleteById(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns list of User inside page object when successful")
    void listAll_ReturnsListOfUsersInsidePageObject_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        Page<User> userPage = userService.listAll(PageRequest.of(0, 5));

        Assertions.assertThat(userPage).isNotNull();

        Assertions.assertThat(userPage.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(userPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("listAllNonPageable returns list of User when successful")
    void listAllNonPageable_ReturnsListOfUsers_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        List<User> users = userService.listAllNonPageable();

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns User when successful")
    void findByIdOrThrowBadRequestException_ReturnsUser_WhenSuccessful() {
        Long expectedId = UserCreator.createValidUser().getId();

        User user = userService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when User is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> userService.findByIdOrThrowBadRequestException(1));

    }

    @Test
    @DisplayName("findByFirstName returns a list of User when successful")
    void findByFirstName_ReturnsListOfUser_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        List<User> users = userService.findByFirstName(expectedFirstName);

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findByFirstName returns an empty list of User when User is not found")
    void findByFirstName_ReturnsEmptyListOfUser_WhenUserIsNotFound() {
        BDDMockito.when(userRepositoryMock.findByFirstName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> users = userService.findByFirstName("John");

        Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns User when successful")
    void save_ReturnsUser_WhenSuccessful() {

        User user = userService.save(UserPostRequestBodyCreator.createUserPostRequestBody());

        Assertions.assertThat(user).isNotNull().isEqualTo(UserCreator.createValidUser());
    }

    @Test
    @DisplayName("replace updates User when successful")
    void replace_UpdatesUser_WhenSuccessful() {

        Assertions.assertThatCode(() -> userService.replace(UserPutRequestBodyCreator.createUserPutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes User when successful")
    void delete_RemovesUser_WhenSuccessful() {

        Assertions.assertThatCode(() -> userService.delete(1)).doesNotThrowAnyException();

    }
}
