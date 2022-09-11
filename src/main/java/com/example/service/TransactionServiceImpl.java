package com.example.service;

import com.example.enums.AccountType;
import com.example.exception.AccountOwnershipException;
import com.example.exception.BadRequestException;
import com.example.exception.BalanceNotSufficientException;
import com.example.exception.UnderConstructionException;
import com.example.model.Account;
import com.example.model.Transaction;
import com.example.repository.AccountRepository;
import com.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;
@Component
public class TransactionServiceImpl implements TransactionService{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TransactionRepository transactionRepository;
    @Value("${under_construction}")
    private boolean underConstruction;
    @Override
    public Transaction makeTransfer(Account sender, Account receiver, BigDecimal amount, Date creationDate, String message) throws BalanceNotSufficientException, UnderConstructionException {
        if (!underConstruction){

        validateAccount(sender,receiver);
        checkAccountOwnership(sender,receiver);
        executeBalanceAndUpdateIfRequired(amount,sender,receiver);

        Transaction transaction = Transaction.builder().sender(sender.getId()).receiver(receiver.getId()).amount(amount).creationDate(creationDate).message(message).build();
        return transactionRepository.save(transaction);
        }else {
            throw new UnderConstructionException("App is under construction, try again later");
        }
    }

    private void executeBalanceAndUpdateIfRequired(BigDecimal amount, Account sender, Account receiver) throws BalanceNotSufficientException {
        if (checkSenderBalance(sender,amount)){
            sender.setBalance(sender.getBalance().subtract(amount));
            receiver.setBalance(receiver.getBalance().subtract(amount));
        }else {
            throw new BalanceNotSufficientException("Balance is not enough for this transfer");
        }
    }

    private boolean checkSenderBalance(Account sender, BigDecimal amount) {
        //verify that sender has enough balance
        return sender.getBalance().subtract(amount).compareTo(BigDecimal.ZERO) >= 0;
    }

    private void checkAccountOwnership(Account sender, Account receiver) {
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

    private void validateAccount(Account sender, Account receiver) {
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

    private void findAccountById(UUID id) {
        accountRepository.findById(id);
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
