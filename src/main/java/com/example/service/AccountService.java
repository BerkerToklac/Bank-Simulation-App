package com.example.service;

import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.model.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface AccountService {

    Account createNewAccount(BigDecimal balance, Date creationDate, AccountType accountType, Long userId, AccountStatus accountStatus);

    List<Account> listAllAccount();

}
