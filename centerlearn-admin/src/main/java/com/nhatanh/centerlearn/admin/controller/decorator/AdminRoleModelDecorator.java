package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.RoleModel;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminRoleModelDecorator {
    private final AdminModelToResponseConverter modelToResponseConverter;

    public PaginationModel<AdminRoleResponse> decorateRoleModel(PaginationModel<RoleModel> roleModelPagination) {
        List<AdminRoleResponse> roleResponses = newArrayList(roleModelPagination.getItems(), this.modelToResponseConverter::toRoleResponse);
        return PaginationModel.<AdminRoleResponse>builder()
            .items(roleResponses)
            .totalPage(roleModelPagination.getTotalPage())
            .currentPage(roleModelPagination.getCurrentPage())
            .build();
    }
}
