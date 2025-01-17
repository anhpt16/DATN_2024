package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.nhatanh.centerlearn.web.repo.AccountRoleRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;

    public List<Long> getRoleIdsByAccountId(long id) {
        List<IdResult> ids = this.accountRoleRepository.findRoleIdsByAccountId(id);
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(ids, IdResult::getId);
    }

}
