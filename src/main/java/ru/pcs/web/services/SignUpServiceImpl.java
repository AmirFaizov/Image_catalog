package ru.pcs.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pcs.web.dto.SignUpForm;
import ru.pcs.web.models.Account;
import ru.pcs.web.repositories.AccountsRepository;

import java.util.Locale;


@Service
@RequiredArgsConstructor
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private final AccountsRepository accountsRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signUp(SignUpForm form) {
        Account account = Account.builder()
                .firstName(form.getFirstName())
                .lastName(form.getLastName())
                .email(form.getEmail().toLowerCase(Locale.ROOT))
                .password(passwordEncoder.encode(form.getPassword()))
                .state(Account.State.CONFIRMED)
                .role(Account.Role.USER)
                .build();

        accountsRepository.save(account);
    }
}
