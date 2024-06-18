package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.AccountsRepository;
import ru.pcs.web.services.AccountsService;
import ru.pcs.web.services.PicturesService;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private final AccountsService accountsService;


    @GetMapping
    public String getAccountPage(Model model){
        model.addAttribute("bannedAccounts", accountsService.getAllBannedAccounts());
        model.addAttribute("accounts", accountsService.getAllAccounts());
        return "accounts";
    }



    @PostMapping("/{account-id}/delete")
    public String deleteAccount(@PathVariable("account-id") Long accountId){
        accountsService.deleteAccount(accountId);
        return "redirect:/accounts";
    }

    @PostMapping("/{account-id}/recover")
    public String recoverAccount(@PathVariable("account-id") Long accountId){
        accountsService.recover(accountId);
        return "redirect:/accounts";
    }

    }
