package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminTextbookModelDecorator {
    private final AdminModelToResponseConverter adminModelToResponseConverter;

    public PaginationModel<AdminTextbookResponse> decorateTextbookPaginationModel(PaginationModel<TextbookModel> textbookPaginationModel) {
        List<AdminTextbookResponse> textbookResponses = newArrayList(textbookPaginationModel.getItems(), this.adminModelToResponseConverter::toTextbookResponse);
        return PaginationModel.<AdminTextbookResponse>builder()
            .items(textbookResponses)
            .totalPage(textbookPaginationModel.getTotalPage())
            .currentPage(textbookPaginationModel.getCurrentPage())
            .build();
    }
}
