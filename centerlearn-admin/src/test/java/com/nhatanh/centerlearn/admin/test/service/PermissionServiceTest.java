package com.nhatanh.centerlearn.admin.test.service;

import com.nhatanh.centerlearn.admin.service.PermissionService;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class PermissionServiceTest implements EzyBeanConfig {
    private final PermissionService permissionService;
    @Override
    public void config() {

        // when
        List<PermissionStatus> statuses = this.permissionService.getAllPermissionStatus();
        // then
    }
}
