package com.example.service.impl;

import com.example.dto.AccountDTO;
import com.example.dto.TransactionDTO;
import com.example.enums.AccountType;
import com.example.exception.AccountOwnershipException;
import com.example.exception.BadRequestException;
import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;
import com.example.mapper.TransactionMapper;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    TransactionMapper transactionMapper;
    @Autowired
    AccountService accountService;
    @Value("${under_construction}")
    private boolean underConstruction;
    @Override
    public void makeTransfer(AccountDTO sender, AccountDTO receiver, BigDecimal amount, Date creationDate, String message) throws BalanceNotSufficientException, UnderConstructionException {
        if (!underConstruction){

        validateAccount(sender,receiver);
        checkAccountOwnership(sender,receiver);
        executeBalanceAndUpdateIfRequired(amount,sender,receiver);

        TransactionDTO transactionDTO = new TransactionDTO(sender,receiver,amount,message,creationDate);
        transactionRepository.save(transactionMapper.convertToEntity(transactionDTO));

        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, AccountDTO sender, AccountDTO receiver) throws BalanceNotSufficientException {
        if (checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().add(amount));

            /*
            get the dto from database for both sender and receiver, update balance and save it.
            create accountService updateAccount method to save it.
             */
            //retrieve the object from database
            AccountDTO senderAcc = accountService.retrieveById(sender.getId());
            senderAcc.setBalance(sender.getBalance());
            //save again to database
            accountService.updateAccount(senderAcc);

            AccountDTO receiverAcc = accountService.retrieveById(receiver.getId());
            receiverAcc.setBalance(receiver.getBalance());
            accountService.updateAccount(receiverAcc);

        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(AccountDTO sender, BigDecimal amount) {
        //verify that sender has enough balance
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(AccountDTO sender, AccountDTO receiver) {
        /*
            - write a if statement that checks if one of the account is saving,
            and user if of sender or receiver is not the same, throw AccountOwnershipException
         */
        if((sender.getAccountType().equals(AccountType.SAVING)
                ||receiver.getAccountType().equals(AccountType.SAVING))&& !sender.getUserId().equals(receiver.getUserId()))
        {
            throw new AccountOwnershipException("One of the accounts is Savings." +
                    " Transactions between savings and checking account are allowed between same user accounts only." +
                    " User Id's dont match.");
        }
    }

    private void validateAccount(AccountDTO sender, AccountDTO receiver) {
        /*
            - if any of the account is null
            - if account ids are the same(same account)
            - if the account exist in the database(repository)
         */
        if (sender==null || receiver==null){
            throw new BadRequestException("Sender or Receiver cannot be null");
        }
        if (sender.getId().equals(receiver.getId())){
            throw new BadRequestException("Sender account needs to be different than receiver");
        }
        findAccountById(sender.getId());
        findAccountById(receiver.getId());

    }

    private void findAccountById(Long id) {
        accountRepository.findById(id);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        return transactionRepository.findAll().stream().map(transactionMapper::convertToDto).collect(Collectors.toList());
    }


    public List<TransactionDTO> lastTransactionsList() {
        return transactionRepository.findLastTenTransaction().stream().map(transactionMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> findTransactionListById(Long id) {
        return transactionRepository.findTransactionListById(id).stream().map(transactionMapper::convertToDto).collect(Collectors.toList());
    }
}
