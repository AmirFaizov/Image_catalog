package ru.pcs.web.security.details;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pcs.web.models.Account;
import ru.pcs.web.repositories.AccountsRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service(value = "userDetailService")
public class AccountUserDetailService implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new AccountUserDetails(
                accountsRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found")));
    }
}
