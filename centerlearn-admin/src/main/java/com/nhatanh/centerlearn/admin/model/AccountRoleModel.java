package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountRoleModel {
    private final long accountId;
    private final long roleId;
}
