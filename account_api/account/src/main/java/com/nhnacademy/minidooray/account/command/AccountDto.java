package com.nhnacademy.minidooray.account.command;

import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AccountDto {

    @NotBlank @Length(min = 5, max = 40)
    private String id;

    @NotBlank @Length(min = 5, max = 50)
    private String email;

    @NotBlank @Length(min = 8, max = 20)
    private String pwd;

    @NotBlank @Length(min = 2, max = 20)
    private String nickname;

    @NotBlank
    private AccountStatus accountStatus;

    @NotBlank
    private SystemAuth systemAuth;
}
