package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminAccountModelDecorator;
import com.nhatanh.centerlearn.admin.controller.decorator.AdminRoleModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.AccountAvatarResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountDetailResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.service.AccountRoleService;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.common.exception.AccountCreationException;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.GalleryModel;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountServiceController {
    private final AccountService accountService;
    private final RoleService roleService;
    private final AccountRoleService accountRoleService;
    private final MediaService mediaService;
    private final AdminModelToModelConverter modelToModelConverter;
    private final AdminAccountModelDecorator accountModelDecorator;
    private final AdminRoleModelDecorator roleModelDecorator;
    private final AdminModelToResponseConverter modelToResponseConverter;
    private final JWTUtil jwtUtil;

    public AccountAvatarResponse getAccountAvatarById(long id) {
        AccountModel accountModel = this.accountService.getAccountById(id);
        if (accountModel == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountAvatar(accountModel);
    }

    public void addAccount(SaveAccountModel accountModel) {
        long accountId = this.accountService.addAccount(accountModel);
        if (accountId == 0) {
            throw new AccountCreationException("Failed to create account");
        }
        AccountRoleModel model = this.modelToModelConverter.toAccountRoleModelConverter(accountId, accountModel.getRoleId());
        this.accountRoleService.addAccountRole(model);
    }

    public List<AdminRoleResponse> getNotAssignedRolesByAccountId(long id) {
        List<RoleModel> roleModels = this.roleService.getNotAssignedRolesByAccountId(id);
        if (roleModels.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(roleModels, this.modelToResponseConverter::toRoleResponse);
    }

    public void addAccountRole(AccountRoleModel accountRoleModel) {
        this.accountRoleService.addAccountRole(accountRoleModel);
    }

    public void deleteAccountRole(AccountRoleModel accountRoleModel) {
        this.accountRoleService.deleteAccountRole(accountRoleModel);
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
        if (model == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountModel(model);
    }

    public AdminAccountResponse getAccountById(long id) {
        AccountModel model = this.accountService.getAccountById(id);
        if (model == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountModel(model);
    }

    public AdminAccountResponse getAccountByPhone(String phone) {
        AccountModel model = this.accountService.getAccountByPhone(phone);
        if (model == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountModel(model);
    }

    public boolean getAccountByUsernameAndPassword(AccountLoginModel accountLoginModel) {
        return this.accountService.getAccountByUsernameAndPassword(accountLoginModel);
    }

    public String getToken(String username) {
        long accountId = this.accountService.getAccountIdByUsername(username);
        if (accountId <= 0 ) {
            throw new ResourceNotFoundException("username");
        }
        List<Long> accountRole = this.accountRoleService.getRoleIdsByAccountId(accountId);
        if (accountRole.isEmpty()) {
            throw new ResourceNotFoundException("role");
        }
        return this.jwtUtil.generateToken(accountId, accountRole);
    }

    public void updateAccountStatus(long accountId, String statusName) {
        this.accountService.updateAccountStatus(accountId, statusName);
    }
}
