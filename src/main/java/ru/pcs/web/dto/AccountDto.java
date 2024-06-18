package ru.pcs.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import ru.pcs.web.models.Account;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .build();
    }

    public static List<AccountDto> from(List<Account> accounts) {
        return accounts.stream().map(AccountDto::from).collect(Collectors.toList());
    }
    public static Page<AccountDto> from(Page<Account> accounts) {
        List<AccountDto> accountDto = accounts.getContent().stream().map(AccountDto::from).collect(Collectors.toList());
        return new PageImpl<>(accountDto, accounts.getPageable(), accounts.getTotalElements());
    }
}
