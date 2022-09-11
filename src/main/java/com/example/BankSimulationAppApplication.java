package com.example;

import com.example.enums.AccountType;
import com.example.exception.BalanceNotSufficientException;
import com.example.model.Account;
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
        Account sender = accountService.createNewAccount(BigDecimal.valueOf(70),new Date(), AccountType.CHECKING,1L);

        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(50),new Date(),AccountType.CHECKING,2L);

        accountService.listAllAccount().forEach(System.out::println);

        transactionService.makeTransfer(sender,receiver,BigDecimal.valueOf(40),new Date(),"Transaction 1");

        System.out.println(transactionService.findAllTransactions().get(0));

        accountService.listAllAccount().forEach(System.out::println);
    }

}
