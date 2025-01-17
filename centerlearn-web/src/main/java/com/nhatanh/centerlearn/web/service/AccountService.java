package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.nhatanh.centerlearn.web.converter.WebEntityToModelConverter;
import com.nhatanh.centerlearn.web.model.AccountLoginModel;
import com.nhatanh.centerlearn.web.model.AccountModel;
import com.nhatanh.centerlearn.web.repo.AccountRepository;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final WebEntityToModelConverter webEntityToModelConverter;

    public boolean getAccountByUsernameAndPassword(AccountLoginModel model) {
        Account account = this.accountRepository.findByUsernameAndPassword(model.getUsername(), model.getPassword());
        return account != null;
    }

    public long getAccountIdByUsername(String username) {
        IdResult result = this.accountRepository.findAccountIdByUsername(username);
        return result == null ? 0L : result.getId();
    }

    public AccountModel getAccountById(long id) {
        Account account = this.accountRepository.findById(id);
        return account == null ? null : this.webEntityToModelConverter.toModel(account);
    }

    public Map<Long, String> getCreatorNameMapByIds(Collection<Long> ids) {
        return EzyMaps.newHashMapNewValues(this.getAccountMapByIds(ids), AccountModel::getDisplayName);
    }

    public Map<Long, AccountModel> getAccountMapByIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyMap() : this.accountRepository.findListByIds(ids).stream()
            .collect(Collectors.toMap(Account::getId, this.webEntityToModelConverter::toModel, (o, n) -> n));
    }
}
