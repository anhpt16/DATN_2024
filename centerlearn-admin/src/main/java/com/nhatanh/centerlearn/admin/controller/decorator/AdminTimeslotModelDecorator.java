package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TimeslotModel;
import com.nhatanh.centerlearn.admin.response.AdminTimeslotResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminTimeslotModelDecorator {
    private final AdminModelToResponseConverter modelToResponseConverter;

    public PaginationModel<AdminTimeslotResponse> decorateTimeslotModel(PaginationModel<TimeslotModel> timeslotModelPagination) {
        List<AdminTimeslotResponse> adminTimeslotResponses = newArrayList(timeslotModelPagination.getItems(), this.modelToResponseConverter::toTimeslotResponse);
        return PaginationModel.<AdminTimeslotResponse>builder()
            .items(adminTimeslotResponses)
            .totalPage(timeslotModelPagination.getTotalPage())
            .currentPage(timeslotModelPagination.getCurrentPage())
            .build();
    }
}
