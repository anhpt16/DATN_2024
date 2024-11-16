package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SaveAccountModel {
    private final String username;
    private final String password;
    private final String displayName;
    private final String email;
    private final String phone;
    private final String status;
    private final long roleId;
}
