package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.AccountRoleModel;
import com.nhatanh.centerlearn.admin.repo.AccountRoleRepository;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;
    private final AdminModelToEntityConverter modelToEntityConverter;
    private final AdminEntityToModelConverter entityToModelConverter;

    public void addAccountRole(AccountRoleModel model) {
        this.accountRoleRepository.save(this.modelToEntityConverter.toAccountRoleEntityConverter(model));
    }

    public List<AccountRoleModel> getAccountRoleByAccountId(long id) {
        List<AccountRole> accountRoles = this.accountRoleRepository.findByAccountId(id);
        return newArrayList(accountRoles, this.entityToModelConverter::toModel);
    }

    public long getRoleIdByAccountId(long id) {
        IdResult roleId = this.accountRoleRepository.findRoleIdByAccountId(id);
        return roleId == null ? 0L : roleId.getId();
    }
}
