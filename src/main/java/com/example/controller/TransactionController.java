package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.dto.TransactionDTO;
import com.example.exception.BalanceNotSufficientException;
import com.example.service.AccountService;
import com.example.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        model.addAttribute("accounts",accountService.listAllActiveAccounts());
        model.addAttribute("transactionDTO", new TransactionDTO());
        model.addAttribute("lastTransactions",transactionService.lastTransactionsList());
        return "transaction/make-transfer";
    }
    @PostMapping("/make-transfer")
    public String getTransactionDetails(@Valid @ModelAttribute("transactionDTO") TransactionDTO transactionDTO, Model model, BindingResult bindingResult) throws BalanceNotSufficientException {
        if (bindingResult.hasErrors()){
            model.addAttribute("accounts",accountService.listAllActiveAccounts());
            return "transaction/make-transfer";
        }
        AccountDTO sender = accountService.retrieveById(transactionDTO.getSender().getId());
        AccountDTO receiver = accountService.retrieveById(transactionDTO.getReceiver().getId());

        transactionService.makeTransfer(sender,receiver, transactionDTO.getAmount(),new Date(), transactionDTO.getMessage());


        return "redirect:/transaction/make-transfer";
    }
    @GetMapping("/transaction/{id}")
    public String getTransactionList(@PathVariable("id") Long id,Model model){
        model.addAttribute("transaction",transactionService.findTransactionListById(id));
        return "transaction/transactions";
    }
}
