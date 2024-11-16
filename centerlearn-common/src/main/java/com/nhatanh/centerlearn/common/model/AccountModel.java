package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AccountModel {
    private final long id;
    private final String username;
    private final String password;
    private final String displayName;
    private final String email;
    private final String phone;
    private final long avatarId;
    private final String status;
    private final long creatorId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
