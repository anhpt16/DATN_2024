package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AccountLoginModel;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.model.SaveAccountModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.repo.AccountRepository;
import com.nhatanh.centerlearn.admin.repo.AccountRepositoryCustom;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountRepositoryCustom accountRepositoryCustom;
    private final AdminModelToEntityConverter modelToEntityConverter;
    private final AdminEntityToModelConverter entityToModelConverter;

    public long getAccountIdByUsername(String username) {
        IdResult result = this.accountRepository.findAccountIdByUsername(username);
        return result == null ? 0L : result.getId();
    }

    public long getAccountIdByEmail(String email) {
        IdResult result = this.accountRepository.findAccountIdByEmail(email);
        return result == null ? 0L : result.getId();
    }

    public long getAccountIdByPhone(String phone) {
        IdResult result = this.accountRepository.findAccountIdByPhone(phone);
        return result == null ? 0L : result.getId();
    }

    public AccountModel getAccountByEmail(String email) {
        Account account = this.accountRepository.findByEmail(email);
        return account == null ? null : this.entityToModelConverter.toModel(account);
    }

    public AccountModel getAccountById(long id) {
        Account account = this.accountRepository.findById(id);
        return account == null ? null : this.entityToModelConverter.toModel(account);
    }

    public String getAccountNameById(long id) {
        return this.accountRepository.findDisplayNameById(id);
    }

    public AccountModel getAccountByPhone(String phone) {
        Account account = this.accountRepository.findByPhone(phone);
        return account == null ? null : this.entityToModelConverter.toModel(account);
    }

    public long addAccount(SaveAccountModel model) {
        Account account = this.modelToEntityConverter.toAccountEntityConverter(model);
        this.accountRepository.save(account);
        return account.getId();
    }

    public PaginationModel<AccountModel> getAccountsByType(AccountFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.accountRepositoryCustom.countAccountByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<AccountModel> accountModels = newArrayList(this.accountRepositoryCustom.findAccountByCriteria(criteria, Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        return PaginationModel.<AccountModel>builder()
            .items(accountModels)
            .totalPage(totalPage)
            .currentPage(page)
            .build();
    }

    public List<AccountStatus> getAllAccountStatus() {
        List<AccountStatus> accountStatuses = Arrays.asList(AccountStatus.values());
        return accountStatuses;
    }

    public boolean getAccountByUsernameAndPassword(AccountLoginModel model) {
        Account account = this.accountRepository.findByUsernameAndPassword(model.getUsername(), model.getPassword());
        return account != null;
    }
}
