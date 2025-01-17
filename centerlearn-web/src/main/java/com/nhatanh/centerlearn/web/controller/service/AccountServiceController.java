package com.nhatanh.centerlearn.web.controller.service;

import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.exception.AccountCreationException;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.service.MediaService;
import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.nhatanh.centerlearn.web.controller.decorator.WebAccountModelDecorator;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.AccountLoginModel;
import com.nhatanh.centerlearn.web.model.AccountModel;
import com.nhatanh.centerlearn.web.response.AccountAvatarResponse;
import com.nhatanh.centerlearn.web.service.AccountRoleService;
import com.nhatanh.centerlearn.web.service.AccountService;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountServiceController {
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final WebAccountModelDecorator webAccountModelDecorator;
    private final JWTUtil jwtUtil;

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

    public AccountAvatarResponse getAccountAvatarById(long id) {
        AccountModel accountModel = this.accountService.getAccountById(id);
        if (accountModel == null) {
            return null;
        }
        return this.webAccountModelDecorator.decorateAccountAvatar(accountModel);
    }
}
