package ru.pcs.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pcs.web.models.Account;

import java.util.List;
import java.util.Optional;


@Repository
public interface AccountsRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    List<Account> findAllByState(Account.State state);

    List<Account> findByLastNameContainingIgnoreCaseOrderByLastName(String lastname);

    Account findByFirstName(String name);


}
