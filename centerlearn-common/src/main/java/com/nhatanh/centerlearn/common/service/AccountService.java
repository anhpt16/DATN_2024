package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.repository.AccountRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private final EntityToModelConverter entityToModelConverter;
    private final AccountRepository accountRepository;

    public AccountModel getAccountById(long accountId) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        }
        if (!account.getStatus().equalsIgnoreCase(AccountStatus.INACTIVE.name())) {
            throw new ResourceNotFoundException("account inactive");
        }
        return this.entityToModelConverter.toModel(account);
    }
}
