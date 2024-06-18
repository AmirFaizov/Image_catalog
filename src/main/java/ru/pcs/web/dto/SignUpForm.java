package ru.pcs.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpForm {
    @NotBlank
    @Size(min = 4, max = 20)
    private String firstName;
    @Size(min = 4, max = 20)
    @NotBlank
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    @Email
    private String email;
}
