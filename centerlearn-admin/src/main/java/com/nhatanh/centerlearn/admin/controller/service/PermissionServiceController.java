package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.admin.validator.PermissionValidator;
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

}
