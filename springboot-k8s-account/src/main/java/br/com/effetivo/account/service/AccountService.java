package br.com.effetivo.account.service;

import org.springframework.stereotype.Service;

import br.com.effetivo.account.exception.NotFoundException;
import br.com.effetivo.account.model.Account;
import br.com.effetivo.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
  
  private final AccountRepository accountRepository;

  public List<Account> findAll(){
    return accountRepository.findAll();
  }

  public Account findById(UUID id) {
    return accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Account not found"));
  }  

  public Account create(Account account) {
    account.setId(UUID.randomUUID());
    Account newAccount = accountRepository.save(account);    
    return newAccount;
  }
  
  public Account update(Account account) {
    return accountRepository.save(account);
  }

  public void delete(UUID id) {
    accountRepository.deleteById(id);
  }

}