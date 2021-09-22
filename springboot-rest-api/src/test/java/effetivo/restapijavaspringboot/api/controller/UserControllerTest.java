package effetivo.restapijavaspringboot.api.controller;

import java.util.Collections;
import java.util.List;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;
import effetivo.restapijavaspringboot.api.request.UserPutRequestBody;
import effetivo.restapijavaspringboot.api.service.UserService;
import effetivo.restapijavaspringboot.api.util.UserCreator;
import effetivo.restapijavaspringboot.api.util.UserPostRequestBodyCreator;
import effetivo.restapijavaspringboot.api.util.UserPutRequestBodyCreator;

@ExtendWith(SpringExtension.class)
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<User> userPage = new PageImpl<>(List.of(UserCreator.createValidUser()));
        BDDMockito.when(userServiceMock.listAll(ArgumentMatchers.any())).thenReturn(userPage);

        BDDMockito.when(userServiceMock.listAllNonPageable()).thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.when(userServiceMock.findByFirstName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(UserCreator.createValidUser()));

        BDDMockito.when(userServiceMock.save(ArgumentMatchers.any(UserPostRequestBody.class)))
                .thenReturn(UserCreator.createValidUser());

        BDDMockito.doNothing().when(userServiceMock).replace(ArgumentMatchers.any(UserPutRequestBody.class));

        BDDMockito.doNothing().when(userServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("findAll returns list of User inside page object when successful")
    void findAll_ReturnsListOfUsersInsidePageObject_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        Page<User> userPage = userController.findAll(null).getBody();

        Assertions.assertThat(userPage).isNotNull();

        Assertions.assertThat(userPage.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(userPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findAll returns list of User when successful")
    void findAll_ReturnsListOfUsers_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        List<User> users = userController.findAllNoPageable().getBody();

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findById returns User when successful")
    void findById_ReturnsUser_WhenSuccessful() {
        Long expectedId = UserCreator.createValidUser().getId();

        User user = userController.findById(1).getBody();

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByFirstName returns a list of User when successful")
    void findByName_ReturnsListOfUser_WhenSuccessful() {
        String expectedFirstName = UserCreator.createValidUser().getFirstName();

        List<User> users = userController.findByFirstName(expectedFirstName).getBody();

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findByFirstName returns an empty list of User when User is not found")
    void findByFirstName_ReturnsEmptyListOfUser_WhenUserIsNotFound() {
        BDDMockito.when(userServiceMock.findByFirstName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        List<User> users = userController.findByFirstName("John").getBody();

        Assertions.assertThat(users).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("save returns User when successful")
    void save_ReturnsUser_WhenSuccessful() {

        User user = userController.save(UserPostRequestBodyCreator.createUserPostRequestBody()).getBody();

        Assertions.assertThat(user).isNotNull().isEqualTo(UserCreator.createValidUser());
    }

    @Test
    @DisplayName("replace updates User when successful")
    void replace_UpdatesUser_WhenSuccessful() {

        Assertions.assertThatCode(() -> userController.replace(UserPutRequestBodyCreator.createUserPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = userController.replace(UserPutRequestBodyCreator.createUserPutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes User when successful")
    void delete_RemovesUser_WhenSuccessful() {

        Assertions.assertThatCode(() -> userController.delete(1)).doesNotThrowAnyException();

        ResponseEntity<Void> entity = userController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
