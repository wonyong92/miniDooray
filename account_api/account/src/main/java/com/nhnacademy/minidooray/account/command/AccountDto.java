package com.nhnacademy.minidooray.account.command;

import com.nhnacademy.minidooray.account.domain.AccountStatus;
import com.nhnacademy.minidooray.account.domain.SystemAuth;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AccountDto {

    @NotNull @NotEmpty @Length(min = 5, max = 40)
    private String id;

    @NotNull @NotEmpty @Length(min = 5, max = 50)
    private String email;

    @NotNull @NotEmpty @Length(min = 8, max = 20)
    private String pwd;

    @NotNull @NotEmpty @Length(min = 2, max = 20)
    private String nickname;

    @NotNull
    private AccountStatus accountStatus;

    @NotNull
    private SystemAuth systemAuth;
}
