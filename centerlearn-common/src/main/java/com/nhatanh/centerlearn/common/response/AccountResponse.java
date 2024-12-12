package com.nhatanh.centerlearn.common.response;

import com.nhatanh.centerlearn.common.enums.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class AccountResponse {
    private final String username;
    private final String displayName;
    private final String email;
    private final String phone;
    private final String avatarUrl;
}
