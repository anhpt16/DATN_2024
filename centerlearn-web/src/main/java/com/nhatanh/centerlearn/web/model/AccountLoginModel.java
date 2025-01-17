package com.nhatanh.centerlearn.web.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AccountLoginModel {
    private final String username;
    private final String password;
}
