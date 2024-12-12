package com.nhatanh.centerlearn.common.controller.controller;

import com.nhatanh.centerlearn.common.controller.decorator.AccountModelDecorator;
import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.response.AccountResponse;
import com.nhatanh.centerlearn.common.service.AccountService;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountServiceController {
    private final AccountService accountService;
    private final ModelToResponseConverter modelToResponseConverter;
    private final AccountModelDecorator accountModelDecorator;

    public void updateAccountAvatar(long accountId, long avatarId) {
        this.accountService.updateAccountAvatar(accountId, avatarId);
    }

    public void updateAccountDisplayName(long accountId, String name) {
        this.accountService.updateAccountDisplayName(accountId, name);
    }

    public void updateAccountEmail(long accountId, String email) {
        this.accountService.updateAccountEmail(accountId, email);
    }

    public void updateAccountPhone(long accountId, String phone) {
        this.accountService.updateAccountPhone(accountId, phone);
    }

    public AccountResponse getAccountById(long accountId) {
        AccountModel accountModel = this.accountService.getAccountById(accountId);
        if (accountModel == null) {
            return null;
        }
        return this.accountModelDecorator.decorateAccountModel(accountModel);
    }
}
