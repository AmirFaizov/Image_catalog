package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pcs.web.models.Account;
import ru.pcs.web.repositories.AccountsRepository;
import ru.pcs.web.services.AccountsService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private final AccountsService accountsService;

    @GetMapping()
    String getProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName();
            Account account = accountsService.findByEmail(username);
            model.addAttribute("account", account);
        }
        return "profile";
    }

}
