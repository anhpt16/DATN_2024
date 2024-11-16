package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.entity.AccountRoleId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface AccountRoleRepository extends EzyDatabaseRepository<AccountRoleId, AccountRole> {
    List<AccountRole> findById(long id);

    AccountRole findByAccountIdAndRoleId(long accountId, long roleId);
}
