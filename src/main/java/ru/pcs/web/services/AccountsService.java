package ru.pcs.web.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import ru.pcs.web.dto.AccountDto;
import ru.pcs.web.models.Account;

import java.util.List;
import java.util.Optional;

public interface AccountsService {
    List<AccountDto> getAllAccounts();

    List<AccountDto> getAllBannedAccounts();

    void deleteAccount(Long accountId);

    void recover(Long accountId);

    Account findByEmail(String email);

    Optional<Account> findByEmailOptional(String email);

    Account save(Account account);

    List<Account> search(String... searchString);

    Page<AccountDto> getAllAccounts(int pageNumber, int pageSize, String sortBy);
}
