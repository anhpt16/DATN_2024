package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.entity.AccountRoleId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface AccountRoleRepository extends EzyDatabaseRepository<AccountRoleId, AccountRole> {

    @EzyQuery("SELECT a.accountId FROM AccountRole a WHERE a.roleId = ?0")
    List<IdResult> findAccountByRoleId(long roleId);

    @EzyQuery("SELECT a.roleId FROM AccountRole a WHERE a.accountId = ?0")
    IdResult findRoleIdByAccountId(long accountId);

    List<AccountRole> findByAccountId(long id);

}
