package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Account;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.time.LocalDateTime;
import java.util.List;

@EzyRepository
public interface AccountRepositoryCustom {
    List<Account> findAccountByCriteria(AccountFilterCriteria criteria, Next next);
    long countAccountByCriteria(AccountFilterCriteria criteria);
}
