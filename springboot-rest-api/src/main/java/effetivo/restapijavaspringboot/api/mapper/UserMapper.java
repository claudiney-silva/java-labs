package effetivo.restapijavaspringboot.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import effetivo.restapijavaspringboot.api.domain.User;
import effetivo.restapijavaspringboot.api.request.UserPostRequestBody;
import effetivo.restapijavaspringboot.api.request.UserPutRequestBody;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toUser(UserPostRequestBody userPostRequestBody);

    public abstract User toUser(UserPutRequestBody userPutRequestBody);
}
