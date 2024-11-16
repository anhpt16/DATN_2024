package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminTermModelDecorator;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.admin.response.AdminTermSuggestionResponse;
import com.nhatanh.centerlearn.admin.service.TermService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Authenticated;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class TermServiceController {
    private final TermService termService;
    private final AdminTermModelDecorator termModelDecorator;

    public List<String> getTypesByTermTypes() {
        return termService.getTypesByTermTypes();
    }

    public List<String> getNamesByTermTypes(String keyword) {
        return termService.getNamesByTermTypes(keyword);
    }
    public long getIdByTermNameAndType(String name, String type) {
        return this.termService.getIdByTermNameAndType(name, type);
    }
    public List<String> getTypesByName(String keyword) {
        return termService.getTypesByName(keyword);
    }
    public AdminTermSuggestionResponse getSuggestions(String keyword) {
        List<String> termSuggestions = getNamesByTermTypes(keyword.trim().toUpperCase());
        List<String> typeSuggestions = getTypesByName(keyword);
        return this.termModelDecorator.toTermSuggestionResponse(termSuggestions, typeSuggestions);
    }

    public PaginationModel<AdminTermResponse> getTermsByTypePagination(String termType, int page, int size) {
        PaginationModel<TermModel> termModelPagination = termService.getTermsByTermTypePagination(termType, page, size);
        return this.termModelDecorator.decorateTermPagination(termModelPagination);
    }

    public PaginationModel<AdminTermResponse> getAllTermPagination(int page, int size) {
        PaginationModel<TermModel> termModelPagination = termService.getAllTermPagination(page, size);
        return this.termModelDecorator.decorateTermPagination(termModelPagination);
    }

}
