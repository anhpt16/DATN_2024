package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminRoleModelDecorator;
import com.nhatanh.centerlearn.admin.controller.service.RoleServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.request.SaveRoleRequest;
import com.nhatanh.centerlearn.admin.request.UpdateRoleRequest;
import com.nhatanh.centerlearn.admin.response.AdminRoleNameResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.admin.validator.FormValidator;
import com.nhatanh.centerlearn.admin.validator.RoleValidator;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.nhatanh.centerlearn.common.utils.UriScanner;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

@Api
@Controller("/api/v1/roles")
@AllArgsConstructor
public class AdminRoleApiController {
    private final RoleValidator roleValidator;
    private final RoleService roleService;
    private final RoleServiceController roleServiceController;
    private final AdminRequestToModelConverter requestToModelConverter;
    private final UriScanner uriScanner;

    @DoPost("/")
    public ResponseEntity addRole(
        @RequestBody SaveRoleRequest request
    ) {
        System.out.println(request.toString());
        this.roleValidator.validate(request);
        this.roleService.addRole(this.requestToModelConverter.toSaveRoleModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoGet("/list-role")
    public List<AdminRoleNameResponse> getRoleName() {
        List<AdminRoleNameResponse> roleNameResponses = this.roleServiceController.getRoleName();
        return roleNameResponses;
    }

    @DoGet("/{id}")
    public AdminRoleResponse getRoleById(
        @PathVariable long id
    ) {
        AdminRoleResponse adminRoleResponse = this.roleServiceController.getRoleById(id);
        return adminRoleResponse;
    }

    @DoGet("/")
    public PaginationModel<AdminRoleResponse> getAllRole(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminRoleResponse> roles = this.roleServiceController.getAllRole(page, size);
        List<UriModel> uris = uriScanner.scanForUris(Constants.URI_SCANNER);
        for (UriModel uri : uris) {
            System.out.println(uri.getUriPath() + "," + uri.getUriMethod());
        }
        return roles;
    }

    @DoPut("/{id}")
    public ResponseEntity updateRoleById(
        @PathVariable long id,
        @RequestBody UpdateRoleRequest request
    ) {
        System.out.println(request.toString());
        this.roleValidator.validate(request, id);
        this.roleService.updateRoleById(id, this.requestToModelConverter.toSaveRoleModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoDelete("/{id}")
    public ResponseEntity deleteRoleById(
        @PathVariable long id
    ) {
        System.out.println(id);
        this.roleService.deleteRoleById(id);
        return ResponseEntity.noContent();
    }
}
