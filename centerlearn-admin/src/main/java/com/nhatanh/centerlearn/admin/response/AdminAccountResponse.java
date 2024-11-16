package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.AccountStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class AdminAccountResponse {
    private final long id;
    private final String username;
    private final String displayName;
    private final String email;
    private final String phone;
    private final AccountStatus status;
    private final long avatarId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
