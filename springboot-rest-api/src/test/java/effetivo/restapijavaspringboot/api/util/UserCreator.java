package effetivo.restapijavaspringboot.api.util;

import effetivo.restapijavaspringboot.api.domain.User;

public class UserCreator {
    public static User createUserToBeSaved() {
        return User.builder().email("john@doe.com").password("123456789").firstName("John").lastName("Doe").build();
    }

    public static User createValidUser() {
        User user = createUserToBeSaved();
        user.setId(1L);
        return user;
    }

    public static User createValidUpdatedUser() {
        User user = createValidUser();
        user.setFirstName("David");
        return user;
    }
}
