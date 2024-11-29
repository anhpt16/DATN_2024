package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminAccountModelDecorator;
import com.nhatanh.centerlearn.admin.controller.decorator.AdminRoleModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AccountLoginModel;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.model.AccountRoleModel;
import com.nhatanh.centerlearn.admin.model.SaveAccountModel;
import com.nhatanh.centerlearn.admin.response.AdminAccountDetailResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.service.AccountRoleService;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.common.exception.AccountCreationException;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountServiceController {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final AdminModelToModelConverter modelToModelConverter;
    private final AdminAccountModelDecorator accountModelDecorator;
    private final AdminRoleModelDecorator roleModelDecorator;
    private final AdminModelToResponseConverter modelToResponseConverter;
    private final JWTUtil jwtUtil;

    public void addAccount(SaveAccountModel accountModel) {
        long accountId = this.accountService.addAccount(accountModel);
        if (accountId == 0) {
            throw new AccountCreationException("Failed to create account");
        }
        AccountRoleModel model = this.modelToModelConverter.toAccountRoleModelConverter(accountId, accountModel.getRoleId());
        this.accountRoleService.addAccountRole(model);
    }

    public void addAccountRole(AccountRoleModel accountRoleModel) {
        this.accountRoleService.addAccountRole(accountRoleModel);
    }

    public AdminAccountDetailResponse getAccountDetailById(long id) {
        AccountModel accountModel = this.accountService.getAccountById(id);
        if (accountModel == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountDetailModel(accountModel);
    }

    public List<AdminRoleResponse> getAccountRolesById(long id) {
        List<Long> roleIds = this.accountRoleService.getRoleIdsByAccountId(id);
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return this.roleModelDecorator.decorateRoleIds(roleIds);
    }

    public PaginationModel<AdminAccountResponse> getAccountsByType(
        AccountFilterCriteria accountFilterCriteria,
        int page,
        int size
    ) {
        PaginationModel<AccountModel> accountModelPagination = this.accountService.getAccountsByType(accountFilterCriteria, page, size);
        return this.accountModelDecorator.decorateAccountModels(accountModelPagination);
    }

    public AdminAccountResponse getAccountByEmail(String email) {
        AccountModel model = this.accountService.getAccountByEmail(email);
        return model == null ? null : this.modelToResponseConverter.toAccountResponse(model);
    }

    public AdminAccountResponse getAccountById(long id) {
        AccountModel model = this.accountService.getAccountById(id);
        return model == null ? null : this.modelToResponseConverter.toAccountResponse(model);
    }

    public AdminAccountResponse getAccountByPhone(String phone) {
        AccountModel model = this.accountService.getAccountByPhone(phone);
        return model == null ? null : this.modelToResponseConverter.toAccountResponse(model);
    }

    public boolean getAccountByUsernameAndPassword(AccountLoginModel accountLoginModel) {
        return this.accountService.getAccountByUsernameAndPassword(accountLoginModel);
    }

    public String getToken(String username) {
        long accountId = this.accountService.getAccountIdByUsername(username);
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
