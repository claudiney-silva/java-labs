package effetivo.jenkinsci.sample.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    private Long id;
    private String firstName;
    private String lastName;
}