package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.entity.Role;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.RoleModel;
import com.nhatanh.centerlearn.common.repository.RoleRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final EntityToModelConverter entityToModelConverter;

    public RoleModel getRoleByRoleId(long roleId) {
        Role role = this.roleRepository.findById(roleId);
        if (role == null) {
            throw new ResourceNotFoundException("Role");
        }
        return this.entityToModelConverter.toModel(role);
    }
}
