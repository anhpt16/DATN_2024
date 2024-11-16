package com.nhatanh.centerlearn.admin.test.service;

import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.service.TermService;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationBefore;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EzyConfigurationAfter
@AllArgsConstructor
public class TermServiceTest implements EzyBeanConfig {
    private final TermService termService;


    @Override
    public void config() {
        Set<Long> ids = new HashSet<>(Arrays.asList(1L));
        // when
        Map<Long, String> mapIdsWithName = termService.getTermNameMapByIds(ids);
        // then
        mapIdsWithName.forEach((id, name) -> System.out.println(id + ":" + name));
    }
}
