package com.nhatanh.centerlearn.common.validator;

import com.nhatanh.centerlearn.common.service.AccountRoleService;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.nhatanh.centerlearn.common.service.RoleService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class AccountValidator {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final RoleService roleService;

    public void validate(long userId, long roleId) {
        this.accountService.getAccountById(userId);
        this.roleService.getRoleByRoleId(roleId);
        this.accountRoleService.getAccountRoleByAccountIdAndRoleId(userId, roleId);
    }
}
