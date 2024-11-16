package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.Permission;
import com.nhatanh.centerlearn.common.entity.PermissionId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface PermissionRepository extends EzyDatabaseRepository<PermissionId, Permission> {
    List<Permission> findByRoleId(long roleId);
    List<Permission> findByRoleIdAndFeatureMethod(long roleId, String featureMethod);
    Permission findByRoleIdAndFeatureUriAndFeatureMethod(long roleId, String featureUri, String featureMethod);
}
