package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface AccountRepository extends EzyDatabaseRepository<Long, Account> {
    Account findById(long accountId);
    Account findByEmail(String email);
    Account findByPhone(String phone);

    @EzyQuery("SELECT a.id FROM Account a WHERE a.email = ?0")
    IdResult findAccountIdByEmail(String email);
    @EzyQuery("SELECT a.id FROM Account a WHERE a.phone = ?0")
    IdResult findAccountIdByPhone(String phone);
}
