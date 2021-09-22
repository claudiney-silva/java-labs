package br.com.effetivo.account.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.effetivo.account.fixtures.AccountHelper;
import br.com.effetivo.account.model.Account;
import br.com.effetivo.account.testcontainer.config.ContainerEnvironment;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountRepositoryTest extends ContainerEnvironment {

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    private void beforeEach() {
        accountRepository.deleteAll();
    }

    @AfterEach
    private void afterEach() {
    }

    @DisplayName("When find all accounts expect an empty list")
    @Test
    public void whenFindAllAccountsExpectEmptyList() {
        List<Account> list = accountRepository.findAll();
        assertEquals(0, list.size());
    }

    @DisplayName("When create a account expect sucessful")
    @Test
    public void whenCreateAccountExpectSucessful() {
        Account accountToBeSaved = AccountHelper.getFilledAccount();
        Account accountSaved = accountRepository.insert(accountToBeSaved);

        assertTrue(accountSaved!=null);
        assertTrue(accountSaved.getId()!=null);
        assertTrue(accountSaved.equals(accountToBeSaved));
    }
    
}
