package com.example.service.impl;

import com.example.entity.Account;
import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.dto.AccountDTO;
import com.example.mapper.AccountMapper;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountMapper accountMapper;

    @Override
    public void createNewAccount(AccountDTO accountDTO) {
        accountDTO.setAccountStatus(AccountStatus.ACTIVE);
        accountDTO.setCreationDate(new Date());
        Account account = accountMapper.convertToEntity(accountDTO);

        accountRepository.save(account);
    }

    @Override
    public List<AccountDTO> listAllAccount() {
        return accountRepository.findAll().stream().map(accountMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).get();
        account.setAccountStatus(AccountStatus.DELETED);
        accountRepository.save(account);
    }

    @Override
    public AccountDTO retrieveById(Long id) {
        return accountMapper.convertToDto(accountRepository.findById(id).get());
    }

    @Override
    public List<AccountDTO> listAllActiveAccounts() {
        List<Account> list = accountRepository.findAllByAccountStatus(AccountStatus.ACTIVE);

        return list.stream().map(accountMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public void updateAccount(AccountDTO accountDTO) {
        accountRepository.save(accountMapper.convertToEntity(accountDTO));
    }
}
