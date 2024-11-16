package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Account;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EzyRepository
public interface AccountRepository extends EzyDatabaseRepository<Long, Account> {

    @EzyQuery("SELECT a.id FROM Account a WHERE a.username = ?0")
    IdResult findAccountByUsername(String username);
    @EzyQuery("SELECT a.id FROM Account a WHERE a.email = ?0")
    IdResult findAccountByEmail(String email);
    @EzyQuery("SELECT a.id FROM Account a WHERE a.phone = ?0")
    IdResult findAccountByPhone(String phone);

    List<Account> findByStatus(String status);
    Account findByUsernameAndPassword(String username, String password);


}
