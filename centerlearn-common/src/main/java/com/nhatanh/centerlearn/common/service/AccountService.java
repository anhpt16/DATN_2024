package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.AccountModel;
import com.nhatanh.centerlearn.common.repository.AccountRepository;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {
    private final EntityToModelConverter entityToModelConverter;
    private final AccountRepository accountRepository;
    private final ModelToEntityConverter modelToEntityConverter;

    public AccountModel getAccountById(long accountId) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        }
        return this.entityToModelConverter.toModel(account);
    }

    public Long getAccountByEmail(String email) {
        IdResult result = this.accountRepository.findAccountIdByEmail(email);
        return result == null ? 0L : result.getId();
    }

    public Long getAccountByPhone(String phone) {
        IdResult result = this.accountRepository.findAccountIdByPhone(phone);
        return result == null ? 0L : result.getId();
    }

    public void updateAccountAvatar(long accountId, long avatarId) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        } else {
            this.modelToEntityConverter.mergeToSaveEntity(account, avatarId);
            this.accountRepository.save(account);
        }
    }

    public void updateAccountDisplayName(long accountId, String displayName) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        } else {
            this.modelToEntityConverter.mergeToSaveAccountWithDisplayName(account, displayName);
            this.accountRepository.save(account);
        }
    }

    public void updateAccountEmail(long accountId, String email) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        } else {
            this.modelToEntityConverter.mergeToSaveAccountWithEmail(account, email);
            this.accountRepository.save(account);
        }
    }

    public void updateAccountPhone(long accountId, String phone) {
        Account account = this.accountRepository.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("account");
        } else {
            this.modelToEntityConverter.mergeToSaveAccountWithPhone(account, phone);
            this.accountRepository.save(account);
        }
    }
}
