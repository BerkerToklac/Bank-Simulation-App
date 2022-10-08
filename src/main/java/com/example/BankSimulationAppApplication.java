package com.example;

import com.example.enums.AccountStatus;
import com.example.enums.AccountType;
import com.example.exception.BalanceNotSufficientException;
import com.example.model.Account;
import com.example.model.Transaction;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) throws BalanceNotSufficientException {
        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

//Get Account and transaction service beans
        AccountService accountService = container.getBean(AccountService.class);
        TransactionService transactionService = container.getBean(TransactionService.class);

        //create 2 account sender and receiver.
        Account sender = accountService.createNewAccount(BigDecimal.valueOf(7000),new Date(), AccountType.CHECKING,1L);

        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(5000),new Date(),AccountType.CHECKING,2L);

        //accountService.listAllAccount().forEach(System.out::println);

        /*
        Transaction transaction = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(40), new Date(), "Transaction 1");
        Transaction transaction1 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(120), new Date(), "Transaction 2");
        Transaction transaction2 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(100), new Date(), "Transaction 3");
        Transaction transaction3 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(55), new Date(), "Transaction 4");
        Transaction transaction4 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(45), new Date(), "Transaction 5");
        Transaction transaction5 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(80), new Date(), "Transaction 6");
        Transaction transaction6 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(110), new Date(), "Transaction 7");
        Transaction transaction7 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(120), new Date(), "Transaction 8");
        Transaction transaction8 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(60), new Date(), "Transaction 9");
        Transaction transaction9 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(200), new Date(), "Transaction 10");
        Transaction transaction10 = transactionService.makeTransfer(sender, receiver, BigDecimal.valueOf(50), new Date(), "Transaction 11");
        */

        //System.out.println(transactionService.findAllTransactions().get(0));

        //accountService.listAllAccount().forEach(System.out::println);

    }

}
