package com.example;

import com.example.dto.AccountDTO;
import com.example.enums.AccountType;
import com.example.exception.BalanceNotSufficientException;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankSimulationAppApplication {

    public static void main(String[] args) throws BalanceNotSufficientException {
        ApplicationContext container = SpringApplication.run(BankSimulationAppApplication.class, args);

//        //get account and transaction service beans
//        AccountService accountService = container.getBean(AccountService.class);
//        TransactionService transactionService = container.getBean(TransactionService.class);
//
//        //create 2 account sender and receiver.
//        Account sender = accountService.createNewAccount(BigDecimal.valueOf(7000),new Date(),
//                                                                AccountType.CHECKING,123L);
//
//        Account receiver = accountService.createNewAccount(BigDecimal.valueOf(5000),new Date(), AccountType.SAVING,145L);
//        Account receiver2 = accountService.createNewAccount(BigDecimal.valueOf(12321),new Date(), AccountType.SAVING,1232L);
//        Account receiver3 = accountService.createNewAccount(BigDecimal.valueOf(4324),new Date(), AccountType.CHECKING,543L);

//
//
//        accountService.listAllAccount().forEach(System.out::println);
//
//        transactionService.makeTransfer(sender,sender,new BigDecimal(40),new Date(),"Transaction 1");
//
//        System.out.println(transactionService.findAllTransaction().get(0));
//
//        accountService.listAllAccount().forEach(System.out::println);
    }
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
