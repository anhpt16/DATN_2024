package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.admin.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.admin.controller.service.PermissionServiceController;
import com.nhatanh.centerlearn.admin.controller.service.RoleServiceController;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.service.AccountService;
import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.common.entity.AccountRole;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/account")
public class AdminAccountViewController {
    private final RoleServiceController roleServiceController;
    private final PermissionService permissionService;
    private final PermissionServiceController permissionServiceController;
    private final AccountServiceController accountServiceController;
    private final RoleService roleService;
    private final AccountService accountService;

    @DoGet("/role")
    public View initRole(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminRoleResponse> roles = this.roleServiceController.getAllRole(page, size);
        return View.builder()
            .addVariable("roles", roles)
            .template("/contents/role/role")
            .build();
    }

    @DoGet("/permission")
    public View initPermission(

    ) {
        List<UriModel> uriModels = this.permissionServiceController.getPermissionByFilter(
            PermissionFilterCriteria.builder()
            .roleId(null)
            .permissonStatus(null)
            .method(null)
            .build()
        );
        List<UriModel> uriModelSorted = this.permissionService.sortUriByPath(uriModels);
        List<RoleModel> roles = this.roleServiceController.getAllRole();
        List<PermissionStatus> permissionStatuses = this.permissionServiceController.getAllPermissionStatus();
        List<MethodName> methodNames = this.permissionServiceController.getAllMethodName();
        return View.builder()
            .addVariable("uriModelSorted", uriModelSorted)
            .addVariable("roles", roles)
            .addVariable("permissionStatuses", permissionStatuses)
            .addVariable("methodNames", methodNames)
            .template("/contents/role/permission")
            .build();
    }


    @DoGet("/user")
    public View initUser(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminAccountResponse> accountPagination = this.accountServiceController.getAccountsByType(
            AccountFilterCriteria.builder().build(),
            page,
            size
        );
        List<AccountStatus> statuses = this.accountService.getAllAccountStatus();
        List<RoleModel> roles = this.roleService.getAllRole();
        return View.builder()
            .addVariable("accountPagination", accountPagination)
            .addVariable("statuses", statuses)
            .addVariable("roles", roles)
            .template("/contents/account/account")
            .build();
    }

    @DoGet("/delegation")
    public View initDelegation() {

        return View.builder()
            .template("/contents/role/delegation")
            .build();
    }

    @DoGet("/student")
    public View initStudent(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminAccountResponse> accountPagination = this.accountServiceController.getAccountsByType(
            AccountFilterCriteria.builder()
                .roleId(2)
                .build(),
            page,
            size
        );
        List<AccountStatus> statuses = this.accountService.getAllAccountStatus();
        return View.builder()
            .addVariable("accountPagination", accountPagination)
            .addVariable("statuses", statuses)
            .template("/contents/account/student")
            .build();
    }
}
