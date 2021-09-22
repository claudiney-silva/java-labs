package effetivo.restapijavaspringboot.api.util;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.request.UserPutRequestBody;

public class UserPutRequestBodyCreator {
    public static UserPutRequestBody createUserPutRequestBody() {
        User user = UserCreator.createValidUser();
        return UserPutRequestBody.builder().id(user.getId()).email(user.getEmail()).password(user.getPassword())
                .firstName(user.getFirstName()).lastName(user.getLastName()).build();
    }
}
