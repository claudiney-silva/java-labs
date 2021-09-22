package br.com.effetivo.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.effetivo.account.dto.request.AccountPostRequestBody;
import br.com.effetivo.account.dto.request.AccountPutRequestBody;
import br.com.effetivo.account.dto.response.AccountResponseBody;
import br.com.effetivo.account.model.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    public static final AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    public abstract Account toAccount(AccountPostRequestBody accountPostRequestBody);

    public abstract Account toAccount(AccountPutRequestBody accountPutRequestBody);

    public abstract AccountResponseBody toAccountResponseBody(Account account);
}
