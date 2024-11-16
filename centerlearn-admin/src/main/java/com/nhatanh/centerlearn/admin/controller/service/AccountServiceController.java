package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminAccountModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AccountLoginModel;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.model.AccountRoleModel;
import com.nhatanh.centerlearn.admin.model.SaveAccountModel;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.service.AccountRoleService;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.common.exception.AccountCreationException;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceController {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final AdminModelToModelConverter modelToModelConverter;
    private final AdminAccountModelDecorator accountModelDecorator;
    private final JWTUtil jwtUtil;

    public void addAccount(SaveAccountModel accountModel) {
        long accountId = this.accountService.addAccount(accountModel);
        if (accountId == 0) {
            throw new AccountCreationException("Failed to create account");
        }
        AccountRoleModel model = this.modelToModelConverter.toAccountRoleModelConverter(accountId, accountModel.getRoleId());
        this.accountRoleService.addAccountRole(model);
    }

    public PaginationModel<AdminAccountResponse> getAccountByType(
        AccountFilterCriteria accountFilterCriteria,
        int page,
        int size
    ) {
        PaginationModel<AccountModel> accountModelPagination = this.accountService.getAccountByType(accountFilterCriteria, page, size);
        return this.accountModelDecorator.decorateAccountModel(accountModelPagination);
    }

    public boolean getAccountByUsernameAndPassword(AccountLoginModel accountLoginModel) {
        return this.accountService.getAccountByUsernameAndPassword(accountLoginModel);
    }

    public String getToken(String username) {
        long accountId = this.accountService.getAccountByUsername(username);
        if (accountId <= 0 ) {
            throw new ResourceNotFoundException("username");
        }
        long accountRole = this.accountRoleService.getRoleIdByAccountId(accountId);
        if (accountRole <= 0) {
            throw new ResourceNotFoundException("role");
        }
        return this.jwtUtil.generateToken(accountId, accountRole);
    }
}
