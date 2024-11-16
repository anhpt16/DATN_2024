package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Role;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface RoleRepository extends EzyDatabaseRepository<Long, Role> {
    Role findById(long roleId);
}
