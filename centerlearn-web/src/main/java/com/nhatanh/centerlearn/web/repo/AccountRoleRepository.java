package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.entity.AccountRoleId;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface AccountRoleRepository extends EzyDatabaseRepository<AccountRoleId, AccountRole> {

    @EzyQuery("SELECT a.roleId FROM AccountRole a WHERE a.accountId = ?0")
    List<IdResult> findRoleIdsByAccountId(long accountId);


}
