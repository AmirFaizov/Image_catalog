package ru.pcs.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.pcs.web.dto.SignUpForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.services.AccountsService;
import ru.pcs.web.services.SignUpService;

import javax.validation.Valid;
import java.util.Optional;


@RequiredArgsConstructor
@Controller
@RequestMapping("/signUp")
public class SignUpController {

    @Autowired
    private final SignUpService signUpService;

    @Autowired
    private final AccountsService accountsService;

    @RequestMapping()
    public String getSignUpPage(Model model, Authentication authentication) {
        if(authentication != null){
            return "redirect:/";
        }
        model.addAttribute("signUpForm", new SignUpForm());
        return "signUp";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String signUp(@Valid SignUpForm form, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("signUpForm", form);
            return "signUp";
        }
        Optional<Account> account = accountsService.findByEmailOptional(form.getEmail());
        if (account.isEmpty()){
            signUpService.signUp(form);
            return "redirect:/signIn";
        }
        else{
            model.addAttribute("signUpForm", form);
            model.addAttribute("error", "пользователь с таким email уже существует");
            return "signUp";
        }
    }
}
