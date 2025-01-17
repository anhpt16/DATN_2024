package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface AccountRepository extends EzyDatabaseRepository<Long, Account> {
    Account findByUsernameAndPassword(String username, String password);

    @EzyQuery("SELECT a.id FROM Account a WHERE a.username = ?0")
    IdResult findAccountIdByUsername(String username);
}
