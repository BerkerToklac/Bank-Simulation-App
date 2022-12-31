package com.example.mapper;

import com.example.dto.TransactionDTO;
import com.example.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final ModelMapper modelMapper;

    public TransactionMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public Transaction convertToEntity(TransactionDTO dto){
        return modelMapper.map(dto,Transaction.class);
    }
    public TransactionDTO convertToDto(Transaction transaction){
        return modelMapper.map(transaction,TransactionDTO.class);
    }
}
