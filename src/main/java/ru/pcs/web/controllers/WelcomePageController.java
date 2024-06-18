package ru.pcs.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.web.models.Account;
import ru.pcs.web.services.AccountsService;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class WelcomePageController {

    @Autowired
    AccountsService accountsService;

    @GetMapping
    public String welcomPage(Model model, Principal principal){
        String email = (principal != null) ? principal.getName() : null;
        Optional<Account> account = (email != null) ? Optional.ofNullable(accountsService.findByEmail(email)) : Optional.empty();
        model.addAttribute("account", account);
        return "main";
    }
}
