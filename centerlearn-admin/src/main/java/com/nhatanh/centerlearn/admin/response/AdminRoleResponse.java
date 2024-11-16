package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class AdminRoleResponse {
    private final long id;
    private final String name;
    private final String displayName;
    private final LocalDateTime createdAt;
}
