package br.com.effetivo.account.fixtures;

import java.util.UUID;

import br.com.effetivo.account.model.Account;
import br.com.effetivo.account.model.Credentials;
import br.com.effetivo.account.model.Settings;

public class AccountHelper {
    public static Account getFilledAccount() {
        return Account.builder()
                .id(UUID.randomUUID())
                .name("John")
                .bio("John's bio")
                .credentials(Credentials.builder().username("john").password("Tes$%23yw").build())
                .settings(Settings.builder().theme("blue").mode("dark").build())
                .build();        
    }
}
