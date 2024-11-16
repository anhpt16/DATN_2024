package com.nhatanh.centerlearn.admin.test.controller.service;

import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.admin.response.AdminTermSuggestionResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class PostServiceControllerTest implements EzyBeanConfig {
    private final TermServiceController termServiceController;

    @Override
    public void config() {

        // when
        PaginationModel<AdminTermResponse> termResponse = termServiceController.getAllTermPagination(1, 10);
        List<String> termTypes = termServiceController.getTypesByTermTypes();
        PaginationModel<AdminTermResponse> termsByTermType = termServiceController.getTermsByTypePagination("menu", 1, 10);
        AdminTermSuggestionResponse termSuggestionResponses = termServiceController.getSuggestions("ME");
        // then
    }
}
