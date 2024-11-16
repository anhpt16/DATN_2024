package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PermissionModel {
    private final long roleId;
    private final String featureUri;
    private final String featureMethod;
    private final LocalDateTime createdAt;
}
