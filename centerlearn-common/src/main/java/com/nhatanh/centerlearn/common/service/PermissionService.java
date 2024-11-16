package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.entity.Permission;
import com.nhatanh.centerlearn.common.model.PermissionModel;
import com.nhatanh.centerlearn.common.repository.PermissionRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;


@Service
@AllArgsConstructor
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final EntityToModelConverter entityToModelConverter;

    public List<PermissionModel> getPermissionByRoleId(long roleId) {
        List<Permission> permissions = this.permissionRepository.findByRoleId(roleId);
        return newArrayList(permissions, this.entityToModelConverter::toPermissionModel);
    }
}
