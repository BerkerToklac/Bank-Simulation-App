package com.example.controller;

import com.example.enums.AccountType;
import com.example.model.Account;
import com.example.repository.AccountRepository;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;


@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;
    /*
        write a method to return index.html page including account list
     */
    @GetMapping("/index")
    public String getIndex(Model model){
        model.addAttribute("accountList", accountService.listAllAccount());
        return "/account/index";
    }

    @GetMapping("/create-form")
    public String getCreateForm(Model model){

        model.addAttribute("account", Account.builder().build());
        model.addAttribute("accountTypes", AccountType.values());
        return "/account/create-account";
    }
    /*
    @PostMapping("/account-list")
    public String getCreateAccountList(@ModelAttribute("create-form") Account account,AccountType accountType, Model model){
        model.addAttribute("accounts", account);
        model.addAttribute("accountType",accountType);

        return "account/account-list";
    }

     */
    @PostMapping("/create")
    public String getCreateAccount(@ModelAttribute("account") Account account){
        accountService.createNewAccount(account.getBalance(),new Date(),account.getAccountType(),account.getUserId());

        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id")UUID id){
        accountService.deleteAccount(id);
        return "redirect:/index";
    }
}
