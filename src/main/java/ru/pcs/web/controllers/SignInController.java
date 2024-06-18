package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.web.models.Account;

import java.net.Authenticator;

@Controller
@RequiredArgsConstructor
@RequestMapping("/signIn")
public class SignInController {

    @GetMapping
    public String getSignInPage(Authentication authentication){
        if(authentication != null){
            return "redirect:/";
        }
        return "signIn";
    }


}
