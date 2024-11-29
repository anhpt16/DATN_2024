package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.Role;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface RoleRepository extends EzyDatabaseRepository<Long, Role> {

    @EzyQuery("SELECT r FROM Role r " +
        "WHERE r.id NOT IN " +
        "(SELECT ar.roleId FROM AccountRole ar " +
        "WHERE ar.accountId = ?0)")
    List<Role> findNotAssignedRolesByAccountId(long id);

    List<Role> findAllRole(Next next);
    Role findByName(String name);
    Role findByDisplayName(String displayName);
    Role findById(long id);
    List<Role> findAllRole();
}
