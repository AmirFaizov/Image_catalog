package ru.pcs.web.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pcs.web.models.Account;

import java.util.Collection;
import java.util.Collections;

public class AccountUserDetails implements UserDetails {

    private final Account account;

    public AccountUserDetails(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = account.getRole().name();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { //Не просрочен ли аккаунт true - ниикогда не бывает
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // не заблокирован ли аккаунт
        return !account.getState().equals(Account.State.BANNED);
    }

    @Override
    public boolean isCredentialsNonExpired() { // не просрочен ли логин - пароль
        return true;
    }

    @Override
    public boolean isEnabled() { // включен ли аккаунт
        return account.getState().equals(Account.State.CONFIRMED);
    }
}
