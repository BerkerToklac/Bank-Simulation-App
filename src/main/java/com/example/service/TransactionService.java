package com.example.service;

import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;
import com.example.model.Account;
import com.example.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionService {

    Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) throws BalanceNotSufficientException, UnderConstructionException;

    List<Transaction> findAllTransactions();
}
