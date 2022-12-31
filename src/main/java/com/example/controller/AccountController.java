package com.example.controller;

import com.example.dto.AccountDTO;
import com.example.enums.AccountType;
import com.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

        model.addAttribute("accountDTO", new AccountDTO());
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
    public String getCreateAccount(@Valid @ModelAttribute("accountDTO") AccountDTO accountDTO, BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("accountTypes",AccountType.values());
            return "account/create-account";
        }
        accountService.createNewAccount(accountDTO);

        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String getDeleteAccount(@PathVariable("id") Long id){
        accountService.deleteAccount(id);
        return "redirect:/index";
    }
}
