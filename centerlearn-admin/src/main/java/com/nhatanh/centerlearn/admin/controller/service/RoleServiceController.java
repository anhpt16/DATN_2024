package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminRoleModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.response.AdminRoleNameResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class RoleServiceController {
    private final RoleService roleService;
    private final AdminRoleModelDecorator roleModelDecorator;
    private final AdminModelToResponseConverter modelToResponseConverter;

    public PaginationModel<AdminRoleResponse> getAllRole(int page, int size) {
        PaginationModel<RoleModel> roleModelPagination = this.roleService.getAllRole(page, size);
        return roleModelDecorator.decorateRoleModel(roleModelPagination);
    }

    public List<AdminRoleNameResponse> getRoleName() {
        List<RoleModel> roleModels = this.roleService.getAllRole();
        return newArrayList(roleModels, this.modelToResponseConverter::toRoleNameResponse);
    }

    public AdminRoleResponse getRoleById(long id) {
        RoleModel roleModel = this.roleService.getRoleById(id);
        return this.modelToResponseConverter.toRoleResponse(roleModel);
    }

    public List<RoleModel> getAllRole() {
        return this.roleService.getAllRole();
    }
}
