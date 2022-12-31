package com.example.service;

import com.example.dto.AccountDTO;
import com.example.dto.TransactionDTO;
import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface TransactionService {

    void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) throws BalanceNotSufficientException, UnderConstructionException;

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> lastTransactionsList();

    List<TransactionDTO> findTransactionListById(Long id);
}
