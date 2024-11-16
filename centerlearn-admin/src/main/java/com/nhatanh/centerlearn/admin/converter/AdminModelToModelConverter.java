package com.nhatanh.centerlearn.admin.converter;

import com.nhatanh.centerlearn.admin.model.AccountRoleModel;
import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.model.TimeslotModel;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.sql.Time;
import java.util.List;

@EzySingleton
@AllArgsConstructor
public class AdminModelToModelConverter {

    public PaginationModel<AdminTermResponse> toTermResponsePagination(List<AdminTermResponse> termResponses, long totalPage, long currentPage) {
        return PaginationModel.<AdminTermResponse>builder()
            .items(termResponses)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<TermModel> toTermModelPagination(List<TermModel> termModels, long totalPage, long currentPage) {
        return PaginationModel.<TermModel>builder()
            .items(termModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<RoomModel> toRoomModelPagination(List<RoomModel> roomModels, long totalPage, long currentPage) {
        return PaginationModel.<RoomModel>builder()
            .items(roomModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public PaginationModel<TimeslotModel> toTimeslotModelPagination(List<TimeslotModel> timeslotModels, long totalPage, long currentPage) {
        return PaginationModel.<TimeslotModel>builder()
            .items(timeslotModels)
            .totalPage(totalPage)
            .currentPage(currentPage)
            .build();
    }

    public AccountRoleModel toAccountRoleModelConverter(long accountId, long roleId) {
        return AccountRoleModel.builder()
            .accountId(accountId)
            .roleId(roleId)
            .build();
    }
}
