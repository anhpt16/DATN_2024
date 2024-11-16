package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class RoleModel {
    private final long id;
    private final String name;
    private final String displayName;
    private final LocalDateTime createdAt;
}
