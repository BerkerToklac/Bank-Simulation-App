package com.example.service;

import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class AccountServiceImpl implements AccountService{

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId) {
        Account account = Account.builder().id(UUID.randomUUID()).balance(balance).creationDate(creationDate).accountType(accountType).userId(userId).accountStatus(AccountStatus.ACTIVE).build();
        return accountRepository.save(account);
    }

    @Override
    public List<Account> listAllAccount() {
        return accountRepository.findAll();
    }

    @Override
    public void deleteAccount(UUID id) {
        Account account = accountRepository.findById(id);
        account.setAccountStatus(AccountStatus.DELETED);
    }

    @Override
    public Account retrieveById(UUID sender) {
        return accountRepository.findById(sender);
    }
}
