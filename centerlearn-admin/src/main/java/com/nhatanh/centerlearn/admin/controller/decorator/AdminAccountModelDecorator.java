package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.AccountModel;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminAccountModelDecorator {
    private final AdminModelToResponseConverter modelToResponseConverter;
    public PaginationModel<AdminAccountResponse> decorateAccountModel(PaginationModel<AccountModel> accountModelPagination) {
        List<AccountModel> accountModels = accountModelPagination.getItems();
        List<AdminAccountResponse> accountResponses = newArrayList(accountModels, this.modelToResponseConverter::toAccountResponse);
        return PaginationModel.<AdminAccountResponse>builder()
            .items(accountResponses)
            .totalPage(accountModelPagination.getTotalPage())
            .currentPage(accountModelPagination.getCurrentPage())
            .build();
    }
}
