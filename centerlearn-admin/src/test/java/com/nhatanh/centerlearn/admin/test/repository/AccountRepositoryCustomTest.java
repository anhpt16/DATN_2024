package com.nhatanh.centerlearn.admin.test.repository;

import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.repo.AccountRepository;
import com.nhatanh.centerlearn.admin.repo.AccountRepositoryCustom;
import com.nhatanh.centerlearn.common.entity.Account;
import com.sun.javaws.IconUtil;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyAutoBind;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfox.util.Next;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class AccountRepositoryCustomTest implements EzyBeanConfig
{
    private final AccountRepositoryCustom accountRepositoryCustom;
    private final AccountRepository accountRepository;
    @Override
    public void config() {

        // when
        AccountFilterCriteria criteria = AccountFilterCriteria.builder()
            .build();
        List<Account> accounts = this.accountRepositoryCustom.findAccountByCriteria(criteria, Next.fromPageSize(3, 2));
        long countByCriteria = this.accountRepositoryCustom.countAccountByCriteria(criteria);
        // then
        System.out.println(accounts.size());
        System.out.println(countByCriteria);
    }
}
