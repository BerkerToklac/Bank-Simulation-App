package com.example.mapper;

import com.example.dto.AccountDTO;
import com.example.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    private final ModelMapper modelMapper;

    public AccountMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AccountDTO convertToDto(Account account){
        return modelMapper.map(account,AccountDTO.class);
    }
    public Account convertToEntity(AccountDTO dto){
        return modelMapper.map(dto,Account.class);
    }
}
