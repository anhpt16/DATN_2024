package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.admin.model.PermissionModel;
import com.nhatanh.centerlearn.admin.model.SavePermissionModel;
import com.nhatanh.centerlearn.admin.repo.PermissionRepository;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.entity.Permission;
import com.nhatanh.centerlearn.common.entity.PermissionId;
import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.nhatanh.centerlearn.common.service.UriService;
import com.nhatanh.centerlearn.common.utils.UriScanner;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class PermissionService {
    private final UriScanner uriScanner;
    private final UriService uriService;
    private final PermissionRepository permissionRepository;
    private final AdminEntityToModelConverter entityToModelConverter;
    private final AdminModelToEntityConverter modelToEntityConverter;

    public List<PermissionStatus> getAllPermissionStatus() {
        List<PermissionStatus> statuses = Arrays.asList(PermissionStatus.values());
        return statuses;
    }

    public List<MethodName> getAllMethodName() {
        List<MethodName> methodNames = Arrays.asList(MethodName.values());
        return methodNames;
    }

    public List<UriModel> getAllPermission(PermissionFilterCriteria permissionFilterCriteria) {
        List<UriModel> uriSystem = uriScanner.scanForUris(Constants.URI_SCANNER);
        if (permissionFilterCriteria.getRoleId() == null) {
                return uriSystem.stream()
                    .filter(uri -> permissionFilterCriteria.getMethod() == null || uri.getUriMethod().name().equalsIgnoreCase(permissionFilterCriteria.getMethod()))
                    .collect(Collectors.toList());
        }
        List<Permission> permissions = this.permissionRepository.findByRoleId(permissionFilterCriteria.getRoleId());
        List<UriModel> uriDatabase = newArrayList(permissions, this.entityToModelConverter::toModel);
        List<UriModel> results = this.uriService.mergeAndSetStatus(uriSystem, uriDatabase);
        return results.stream()
            .filter(uri -> permissionFilterCriteria.getPermissonStatus() == null || uri.getStatus().getCode() == permissionFilterCriteria.getPermissonStatus())
            .filter(uri -> permissionFilterCriteria.getMethod() == null || uri.getUriMethod().name().equalsIgnoreCase(permissionFilterCriteria.getMethod()))
            .collect(Collectors.toList());
    }

    public List<UriModel> sortUriByPath(List<UriModel> uriModels) {
        return uriModels.stream()
            .sorted((uri1, uri2) -> uri1.getUriPath().compareToIgnoreCase(uri2.getUriPath()))
            .collect(Collectors.toList());
    }

    public PermissionModel getPermissionByUnique(long roleId, String featureUri, String featureMethod) {
        Permission permission = this.permissionRepository.findByRoleIdAndFeatureUriAndFeatureMethod(roleId, featureUri, featureMethod);
        if (permission == null) {
            return null;
        }
        return this.entityToModelConverter.toPermissionModel(permission);
    }

    public void addPermission(SavePermissionModel model) {
        this.permissionRepository.save(this.modelToEntityConverter.toPermissionEntityConverter(model));
    }

    public void deletePermission(PermissionId permissionId) {
        Permission permission = this.permissionRepository.findById(permissionId);
        if (permission == null) {
            throw new ResourceNotFoundException("PermissionId");
        } else {
            this.permissionRepository.delete(permissionId);
        }
    }
}
