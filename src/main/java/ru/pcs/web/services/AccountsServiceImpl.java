package ru.pcs.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pcs.web.dto.AccountDto;
import ru.pcs.web.models.Account;
import ru.pcs.web.models.Picture;
import ru.pcs.web.repositories.AccountsRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static ru.pcs.web.models.Account.State.CONFIRMED;
import static ru.pcs.web.models.Account.State.DELETED;

@Service
@RequiredArgsConstructor
public class AccountsServiceImpl implements AccountsService {

    @Autowired
    private final AccountsRepository accountsRepository;


    @Override
    public List<AccountDto> getAllAccounts() {
        return AccountDto.from(accountsRepository.findAllByState(CONFIRMED));
    }

    @Override
    public List<AccountDto> getAllBannedAccounts() {
        return AccountDto.from(accountsRepository.findAllByState(DELETED));
    }

    @Override
    public Page<AccountDto> getAllAccounts(int pageNumber, int pageSize, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return AccountDto.from(accountsRepository.findAll(pageable));
    }



    @Override
    public void deleteAccount(Long accountId) {
        Optional<Account> accountOptional = accountsRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setState(DELETED);
            accountsRepository.save(account);
        } else {
            new UsernameNotFoundException("User not found");
        }
    }

    public void recover(Long accountId) {
        Optional<Account> accountOptional = accountsRepository.findById(accountId);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setState(CONFIRMED);
            accountsRepository.save(account);
        } else {
            new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public Account findByEmail(String email) {
        Optional<Account> accountOptional = accountsRepository.findByEmail(email);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            return account;
        }
        else {
            throw new NoSuchElementException("Account with email " + email + " not found");
        }
    }

    @Override
    public Optional<Account> findByEmailOptional(String email) {
        Optional<Account> accountOptional = accountsRepository.findByEmail(email);
        if (accountOptional.isPresent()) {
            return accountOptional;
        }
        else {
           return Optional.empty();
        }
    }


    @Override
    public Account save(Account account) {
        return accountsRepository.save(account);
    }

    @Override
    public List<Account> search(String... searchString) {
        return accountsRepository.findByLastNameContainingIgnoreCaseOrderByLastName(searchString[0]);
    }
}
