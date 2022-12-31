package com.example.converter;


import com.example.dto.AccountDTO;
import com.example.entity.Account;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

public class AccountDtoConverter implements Converter<String, AccountDTO> {
    @Autowired
    AccountService accountService;

    @Override
    public AccountDTO convert(String source) {
        if (source==null || source.equals("")){
            return null;
        }

        return accountService.retrieveById(Long.parseLong(source));
    }
}
