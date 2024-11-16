package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.model.SaveRoleModel;
import com.nhatanh.centerlearn.admin.repo.RoleRepository;
import com.nhatanh.centerlearn.common.entity.Role;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final AdminEntityToModelConverter entityToModelConverter;
    private final AdminModelToEntityConverter modelToEntityConverter;

    public RoleModel getRoleByName(String name) {
        Role role = this.roleRepository.findByName(name);
        return role == null ? null : this.entityToModelConverter.toModel(role);
    }

    public List<RoleModel> getAllRole() {
        return newArrayList(this.roleRepository.findAllRole(), this.entityToModelConverter::toModel);
    }

    public RoleModel getRoleById(long id) {
        Role role = this.roleRepository.findById(id);
        if (role == null) {
            throw new ResourceNotFoundException("role");
        } else {
            return this.entityToModelConverter.toModel(role);
        }
    }

    public RoleModel getRoleByDisplayName(String displayName) {
        Role role = this.roleRepository.findByDisplayName(displayName);
        return role == null ? null : this.entityToModelConverter.toModel(role);
    }

    public void addRole(SaveRoleModel model) {
        this.roleRepository.save(this.modelToEntityConverter.toRoleEntityConverter(model));
    }

    public void deleteRoleById(long id) {
        Role role = roleRepository.findById(id);
        if (role == null) {
            throw new ResourceNotFoundException("role");
        } else {
            this.roleRepository.delete(id);
        }
    }

    public void updateRoleById(long id, SaveRoleModel model) {
        Role entity = this.roleRepository.findById(id);
        if (entity == null) {
            throw new ResourceNotFoundException("role");
        } else {
            this.modelToEntityConverter.mergeToSaveEntity(model, entity);
            this.roleRepository.save(entity);
        }
    }

    public PaginationModel<RoleModel> getAllRole(int page, int size) {
        long totalPage = (long) Math.ceil((double) this.roleRepository.count() / size);
        List<RoleModel> roomModels = newArrayList(this.roleRepository.findAllRole(Next.fromPageSize(page, size)), entityToModelConverter::toModel);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        return PaginationModel.<RoleModel>builder()
            .items(roomModels)
            .totalPage(totalPage)
            .currentPage(page)
            .build();
    }
}
