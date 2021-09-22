package effetivo.restapijavaspringboot.api.util;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;

public class UserPostRequestBodyCreator {
    public static UserPostRequestBody createUserPostRequestBody() {
        User user = UserCreator.createUserToBeSaved();
        return UserPostRequestBody.builder().email(user.getEmail()).password(user.getPassword())
                .firstName(user.getFirstName()).lastName(user.getLastName()).build();
    }
}
