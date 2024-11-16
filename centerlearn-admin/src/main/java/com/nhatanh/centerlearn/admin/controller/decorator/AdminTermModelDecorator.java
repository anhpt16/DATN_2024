package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminItemsToResponseConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.admin.response.AdminTermSuggestionResponse;
import com.nhatanh.centerlearn.admin.service.TermService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminTermModelDecorator {
    private final TermService termService;
    private final AdminModelToResponseConverter modelToResponseConverter;
    private final AdminItemsToResponseConverter itemsToResponseConverter;

    public List<AdminTermResponse> decorateTermItem(List<TermModel> termModels) {
        Set<Long> parentIds = termModels
            .stream()
            .map(TermModel::getParentId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> parentIdsWithNames = termService.getTermNameMapByIds(parentIds);
        return newArrayList(
            termModels,
            termModel -> modelToResponseConverter.toTermItemResponse(
                termModel,
                parentIdsWithNames.get(termModel.getParentId())
            )
        );
    }

    public AdminTermSuggestionResponse toTermSuggestionResponse(List<String> name, List<String> type) {
        return itemsToResponseConverter.toSuggestionResponse(name, type);
    }

    public PaginationModel<AdminTermResponse> decorateTermPagination(PaginationModel<TermModel> termModelPagination) {
        return PaginationModel.<AdminTermResponse>builder()
            .items(this.decorateTermItem(termModelPagination.getItems()))
            .totalPage(termModelPagination.getTotalPage())
            .currentPage(termModelPagination.getCurrentPage())
            .build();
    }
}
