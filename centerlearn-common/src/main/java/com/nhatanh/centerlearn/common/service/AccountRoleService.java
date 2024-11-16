package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.AccountRoleModel;
import com.nhatanh.centerlearn.common.repository.AccountRoleRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;
    private final EntityToModelConverter entityToModelConverter;

    public List<AccountRoleModel> getAccountByAccountId(long accountId) {
        List<AccountRole> accountRoles = this.accountRoleRepository.findById(accountId);
        return newArrayList(accountRoles, this.entityToModelConverter::toModel);
    }

    public AccountRoleModel getAccountRoleByAccountIdAndRoleId(long accountId, long roleId) {
        AccountRole accountRole = this.accountRoleRepository.findByAccountIdAndRoleId(accountId, roleId);
        if (accountRole == null) {
            throw new ResourceNotFoundException("AccountRole");
        }
        return entityToModelConverter.toModel(accountRole);
    }
}
