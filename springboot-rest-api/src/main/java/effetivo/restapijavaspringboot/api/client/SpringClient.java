package effetivo.restapijavaspringboot.api.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import effetivo.restapijavaspringboot.api.domain.User;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SpringClient {

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }

    public static void main(String[] args) {
        ResponseEntity<User> entity = new RestTemplate().getForEntity("http://localhost:8080/users/{id}", User.class,
                1);
        log.info(entity);

        User object = new RestTemplate().getForObject("http://localhost:8080/users/{1}", User.class, 2);
        log.info(object);

        User[] users = new RestTemplate().getForObject("http://localhost:8080/users/all", User[].class);
        log.info(Arrays.toString(users));

        ResponseEntity<List<User>> exchange = new RestTemplate().exchange("http://localhost:8080/users/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {

                });
        log.info(exchange.getBody());

        // User user =
        // User.builder().email("post@post.com").password("password").firstName("Test").lastName("teste2")
        // .build();
        // User userSaved = new
        // RestTemplate().postForObject("http://localhost:8080/users", user,
        // User.class);
        // log.info("userSaved {}", userSaved);

        User user = User.builder().email("post@post.com").password("password").firstName("Test").lastName("teste2")
                .build();
        ResponseEntity<User> userSaved = new RestTemplate().exchange("http://localhost:8080/users", HttpMethod.POST,
                new HttpEntity<>(user, createJsonHeader()), User.class);
        log.info("userSaved {}", userSaved);

        User userToBeUpdated = userSaved.getBody();
        userToBeUpdated.setEmail("email@novo.com");
        ResponseEntity<Void> userUpdated = new RestTemplate().exchange("http://localhost:8080/users", HttpMethod.PUT,
                new HttpEntity<>(userToBeUpdated, createJsonHeader()), Void.class);

        log.info("updated -->", userUpdated);

        ResponseEntity<Void> userDeleted = new RestTemplate().exchange("http://localhost:8080/users/{id}",
                HttpMethod.DELETE, new HttpEntity<>(null, createJsonHeader()), Void.class, userToBeUpdated.getId());

        log.info("deleted -->", userDeleted);
    }
}
