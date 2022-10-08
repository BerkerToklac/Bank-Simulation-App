package com.example.controller;

import com.example.exception.BalanceNotSufficientException;
import com.example.model.Account;
import com.example.model.Transaction;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

@Controller
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/make-transfer")
    public String makeTransaction(Model model){
        model.addAttribute("accounts",accountService.listAllAccount());
        model.addAttribute("transaction",Transaction.builder().build());
        model.addAttribute("lastTransactions",transactionService.lastTransactionsList());
        return "transaction/make-transfer";
    }
    @PostMapping("/make-transfer")
    public String getTransactionDetails(@ModelAttribute("transaction")Transaction transaction, Model model) throws BalanceNotSufficientException {
        Account sender = accountService.retrieveById(transaction.getSender());
        Account receiver = accountService.retrieveById(transaction.getReceiver());
        transactionService.makeTransfer(sender,receiver,transaction.getAmount(),new Date(),transaction.getMessage());


        return "redirect:/transaction/make-transfer";
    }
    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") UUID id,Model model){
        model.addAttribute("transaction",transactionService.findTransactionListById(id));
        return "transaction/transactions";
    }
}
