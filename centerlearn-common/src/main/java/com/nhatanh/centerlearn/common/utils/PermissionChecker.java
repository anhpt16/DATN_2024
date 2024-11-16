package com.nhatanh.centerlearn.common.utils;

import com.nhatanh.centerlearn.common.model.PermissionModel;
import com.nhatanh.centerlearn.common.model.UriRequestModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EzySingleton
public class PermissionChecker {
    public boolean isPermissionGranted(List<PermissionModel> permissions, UriRequestModel uriRequest) {
        UriMatcher matcher = new UriMatcher();
        // Sử dụng Stream API để duyệt qua danh sách PermissionModel
        return permissions.stream().anyMatch(permission ->
            permission.getRoleId() == uriRequest.getRoleId() &&
                permission.getFeatureMethod().equalsIgnoreCase(uriRequest.getMethod()) &&
                matcher.isUriMatch(uriRequest.getPath(), Collections.singletonList(permission.getFeatureUri()))
        );
    }
}
