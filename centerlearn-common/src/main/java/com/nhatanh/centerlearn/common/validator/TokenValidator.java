package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.service.AccountRoleService;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.nhatanh.centerlearn.common.service.RoleService;
import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@EzySingleton
@AllArgsConstructor
public class TokenValidator {
    private final JWTUtil jwtUtil;
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final RoleService roleService;

    public boolean validate(String token) {
        if (!jwtUtil.validateToken(token)) {
            return false;
        }
        return true;
    }
}
