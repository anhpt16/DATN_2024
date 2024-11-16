package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.repo.PermissionRepository;
import com.nhatanh.centerlearn.admin.request.SavePermissionRequest;
import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.nhatanh.centerlearn.common.service.UriService;
import com.nhatanh.centerlearn.common.utils.UriScanner;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class PermissionValidator {
    private final RoleService roleService;
    private final FormValidator formValidator;
    private final UriScanner uriScanner;
    private final PermissionService permissionService;

    public boolean validateRoleId(Long id) {
        if (formValidator.validateNull(id)) {
            return this.roleService.getRoleById(id) != null;
        }
        return true;
    }

    public boolean validatePermissionStatus(Integer statusCode) {
        if (formValidator.validateNull(statusCode)) {
            return Arrays.stream(PermissionStatus.values())
                .anyMatch(status -> status.getCode() == statusCode);
        }
        return true;
    }

    public boolean validateMethodName(String method) {
        if (formValidator.validateNull(method)) {
            return Arrays.stream(MethodName.values())
                .anyMatch(methodName -> methodName.getDisplayName().equalsIgnoreCase(method));
        }
        return true;
    }

    public void validate(Long roleId, Integer status, String method) {
        if (!validateRoleId(roleId)) {
            throw new ResourceNotFoundException("RoleId");
        }
        if (!validatePermissionStatus(status)) {
            throw new ResourceNotFoundException("Status");
        }
        if (!validateMethodName(method)) {
            throw new ResourceNotFoundException("Method");
        }
    }

    public void validateToSave(SavePermissionRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check blank
        if (!formValidator.validateNull(request.getRoleId())) {
            errors.put("RoleId", "Null");
        }
        if (formValidator.validateBlank(request.getUriFeature())) {
            errors.put("UriFeature", "Blank");
        }
        if (formValidator.validateBlank(request.getUriMethod())) {
            errors.put("UriMethod", "Blank");
        }
        // Check invalid
        /// RoleId Exist
        if (this.roleService.getRoleById(request.getRoleId()) == null) {
            errors.put("RoleId", "is not exist");
        }
        /// Path and Method Exist
        List<UriModel> uris = this.uriScanner.scanForUris(Constants.URI_SCANNER);
        boolean isExist = uris.stream()
            .anyMatch(uriModel -> uriModel.getUriPath().equals(request.getUriFeature())
                && uriModel.getUriMethod().name().equals(request.getUriMethod()));
        if (!isExist) {
            errors.put("Path-Method", "is not exist");
        }
        /// RoleId - Path - Method Exist
        if (this.permissionService.getPermissionByUnique(request.getRoleId(), request.getUriFeature(), request.getUriMethod()) != null) {
            errors.put("Id-Path-Method", "Exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateToDelete(SavePermissionRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check blank
        if (!formValidator.validateNull(request.getRoleId())) {
            errors.put("RoleId", "Null");
        }
        if (formValidator.validateBlank(request.getUriFeature())) {
            errors.put("UriFeature", "Blank");
        }
        if (formValidator.validateBlank(request.getUriMethod())) {
            errors.put("UriMethod", "Blank");
        }
        // Check invalid
        /// RoleId Exist
        if (this.roleService.getRoleById(request.getRoleId()) == null) {
            errors.put("RoleId", "is not exist");
        }
        /// RoleId - Path - Method Exist
        if (this.permissionService.getPermissionByUnique(request.getRoleId(), request.getUriFeature(), request.getUriMethod()) == null) {
            errors.put("Id-Path-Method", "is not exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

}
