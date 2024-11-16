package com.nhatanh.centerlearn.admin.test.controller.service;

import com.nhatanh.centerlearn.admin.controller.service.PermissionServiceController;
import com.nhatanh.centerlearn.admin.filter.PermissionFilterCriteria;
import com.nhatanh.centerlearn.common.model.UriModel;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class PermissionServiceControllerTest implements EzyBeanConfig{
    private final PermissionServiceController permissionServiceController;

    @Override
    public void config() {

        // when
        List<UriModel> uriModelList = this.permissionServiceController.getPermissionByFilter(
            PermissionFilterCriteria.builder()
            .roleId(2L)
            .permissonStatus(null)
            .method(null)
            .build()
        );
        // then
    }
}
