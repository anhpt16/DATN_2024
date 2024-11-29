package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.admin.model.SavePermissionModel;
import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.admin.validator.PermissionValidator;
import com.nhatanh.centerlearn.common.entity.PermissionId;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class PermissionServiceController {
    private final PermissionService permissionService;

    public List<PermissionStatus> getAllPermissionStatus() {
        return this.permissionService.getAllPermissionStatus();
    }

    public List<MethodName> getAllMethodName() {
        return this.permissionService.getAllMethodName();
    }

    public List<UriModel> getPermissionByFilter(PermissionFilterCriteria permissionFilterCriteria) {
        return this.permissionService.getAllPermission(permissionFilterCriteria);
    }

    public List<UriModel> getAllPermission(PermissionFilterCriteria permissionFilterCriteria) {
        return this.permissionService.getAllPermission(permissionFilterCriteria);
    }

    public void addPermission(SavePermissionModel model) {
        this.permissionService.addPermission(model);
    }

    public void deletePermission(PermissionId permissionId) {
        this.permissionService.deletePermission(permissionId);
    }

}
