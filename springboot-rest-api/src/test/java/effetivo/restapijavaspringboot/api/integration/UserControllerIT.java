package effetivo.restapijavaspringboot.api.integration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
// import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import effetivo.restapijavaspringboot.api.domain.Auth;
import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.repository.AuthRepository;
import effetivo.restapijavaspringboot.api.repository.UserRepository;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;
import effetivo.restapijavaspringboot.api.util.UserCreator;
import effetivo.restapijavaspringboot.api.util.UserPostRequestBodyCreator;
import effetivo.restapijavaspringboot.api.wrapper.PageableResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserControllerIT {
    @Autowired
    @Qualifier(value="testRestTemplateRoleUser")
    private TestRestTemplate testRestTemplateRoleUser;

    @Autowired
    @Qualifier(value="testRestTemplateRoleAdmin")
    private TestRestTemplate testRestTemplateRoleAdmin;    
    // @LocalServerPort
    // private int port;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthRepository authRepository;
    
    private static final Auth USER = Auth.builder()
            .password("{bcrypt}$2a$10$CEbaYPLGYsRrLq1P87uxI.VqHjzWuF95LyhzxlZ12qMTQ1IV5XwFq")
            .username("usuario")
            .authorities("ROLE_USER")
            .build();

    private static final Auth ADMIN = Auth.builder()
            .password("{bcrypt}$2a$10$CEbaYPLGYsRrLq1P87uxI.VqHjzWuF95LyhzxlZ12qMTQ1IV5XwFq")
            .username("admin")
            .authorities("ROLE_USER,ROLE_ADMIN")
            .build();    

    @TestConfiguration
    @Lazy
    static class Config {

        @Bean(name = "testRestTemplateRoleUser")
        public TestRestTemplate testRestTemplateRoleUserCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("usuario", "123456789");
            return new TestRestTemplate(restTemplateBuilder);
        }

        @Bean(name = "testRestTemplateRoleAdmin")
        public TestRestTemplate testRestTemplateRoleAdminCreator(@Value("${local.server.port}") int port) {
            RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder()
                    .rootUri("http://localhost:"+port)
                    .basicAuthentication("admin", "123456789");
            return new TestRestTemplate(restTemplateBuilder);
        }
    }

    @Test
    @DisplayName("list returns list of User inside page object when successful")
    void list_ReturnsListOfUsersInsidePageObject_WhenSuccessful() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        String expectedFirstName = savedUser.getFirstName();

        PageableResponse<User> userPage = testRestTemplateRoleUser
                .exchange("/users", HttpMethod.GET, null, new ParameterizedTypeReference<PageableResponse<User>>() {
                }).getBody();

        Assertions.assertThat(userPage).isNotNull();

        Assertions.assertThat(userPage.toList()).isNotEmpty().hasSize(1);

        Assertions.assertThat(userPage.toList().get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("listAll returns list of User when successful")
    void listAll_ReturnsListOfUsers_WhenSuccessful() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        String expectedFirstName = savedUser.getFirstName();

        List<User> users = testRestTemplateRoleUser
                .exchange("/users/all", HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findById returns User when successful")
    void findById_ReturnsUser_WhenSuccessful() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        Long expectedId = savedUser.getId();

        User user = testRestTemplateRoleUser.getForObject("/users/{id}", User.class, expectedId);

        Assertions.assertThat(user).isNotNull();

        Assertions.assertThat(user.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByName returns a list of User when successful")
    void findByName_ReturnsListOfUser_WhenSuccessful() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        String expectedFirstName = savedUser.getFirstName();

        String url = String.format("/users/search?firstName=%s", expectedFirstName);

        List<User> users = testRestTemplateRoleUser
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(users).isNotNull().isNotEmpty().hasSize(1);

        Assertions.assertThat(users.get(0).getFirstName()).isEqualTo(expectedFirstName);
    }

    @Test
    @DisplayName("findByName returns an empty list of User when anime is not found")
    void findByName_ReturnsEmptyListOfUser_WhenAnimeIsNotFound() {
        authRepository.save(USER);
        List<User> animes = testRestTemplateRoleUser.exchange("/users/search?firstName=dbz", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<User>>() {
                }).getBody();

        Assertions.assertThat(animes).isNotNull().isEmpty();

    }

    @Test
    @DisplayName("save returns User when successful")
    void save_ReturnsUser_WhenSuccessful() {
        authRepository.save(ADMIN);
        UserPostRequestBody userPostRequestBody = UserPostRequestBodyCreator.createUserPostRequestBody();

        ResponseEntity<User> userResponseEntity = testRestTemplateRoleAdmin.postForEntity("/users", userPostRequestBody,
                User.class);

        Assertions.assertThat(userResponseEntity).isNotNull();
        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(userResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(userResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates User when successful")
    void replace_UpdatesUser_WhenSuccessful() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        savedUser.setFirstName("David");

        ResponseEntity<Void> userResponseEntity = testRestTemplateRoleUser.exchange("/users", HttpMethod.PUT,
                new HttpEntity<>(savedUser), Void.class);

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes User when successful")
    void delete_RemovesUser_WhenSuccessful() {
        authRepository.save(ADMIN);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        ResponseEntity<Void> userResponseEntity = testRestTemplateRoleAdmin.exchange("/users/admin/{id}", HttpMethod.DELETE, null,
                Void.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns 403 when Auth is not admin")
    void delete_Returns403_WhenAuthIsNotAdmin() {
        authRepository.save(USER);
        User savedUser = userRepository.save(UserCreator.createUserToBeSaved());

        ResponseEntity<Void> userResponseEntity = testRestTemplateRoleUser.exchange("/users/admin/{id}", HttpMethod.DELETE, null,
                Void.class, savedUser.getId());

        Assertions.assertThat(userResponseEntity).isNotNull();

        Assertions.assertThat(userResponseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
