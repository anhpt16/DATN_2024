package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.PermissionServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.admin.request.SavePermissionRequest;
import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.admin.validator.PermissionValidator;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.List;

@Api
@Controller("/api/v1/permissions")
@AllArgsConstructor
public class AdminPermissionApiController {
    private final PermissionService permissionService;
    private final PermissionServiceController permissionServiceController;
    private final PermissionValidator permissionValidator;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoGet("/by-type")
    public List<UriModel> getPermissionByType(
        @RequestParam("roleId") Long roleId,
        @RequestParam("status") Integer status,
        @RequestParam("method") String method
    ) {
        this.permissionValidator.validate(roleId, status, method);
        List<UriModel> uriModels = this.permissionServiceController.getPermissionByFilter(
            PermissionFilterCriteria.builder()
            .roleId(roleId)
            .permissonStatus(status)
            .method(method)
            .build()
        );
        return uriModels;
    }

    @DoPost("/add")
    public ResponseEntity addPermission(
        @RequestBody SavePermissionRequest request
    ) {
        this.permissionValidator.validateToSave(request);
        this.permissionService.addPermission(this.requestToModelConverter.toSavePermissionModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoDelete("/delete")
    public ResponseEntity deletePermission(
        @RequestBody SavePermissionRequest request
    ) {
        this.permissionValidator.validateToDelete(request);
        this.permissionService.deletePermission(this.requestToModelConverter.toPermissionId(request));
        return ResponseEntity.noContent();
    }

    @DoPost("/add-all")
    public ResponseEntity addAllPermission(
        @RequestBody List<SavePermissionRequest> requests
    ) {
        requests.forEach(this.permissionValidator::validateToSave);
        requests.forEach(request -> this.permissionService.addPermission(this.requestToModelConverter.toSavePermissionModelConverter(request)));
        return ResponseEntity.noContent();
    }

    @DoDelete("/delete-all")
    public ResponseEntity deleteAllPermission(
        @RequestBody List<SavePermissionRequest> requests
    ) {
        requests.forEach(this.permissionValidator::validateToDelete);
        requests.forEach(request -> this.permissionService.deletePermission(this.requestToModelConverter.toPermissionId(request)));
        return ResponseEntity.noContent();
    }
}
