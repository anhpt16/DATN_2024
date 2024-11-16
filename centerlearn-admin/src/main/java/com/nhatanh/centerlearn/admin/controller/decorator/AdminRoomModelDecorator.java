package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.response.AdminRoomBaseResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.service.RoomService;
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
public class AdminRoomModelDecorator {
    public final RoomService roomService;
    private final TermService termService;
    public final AdminModelToResponseConverter modelToResponseConverter;

    public List<AdminRoomResponse> decorateRoomItem(List<RoomModel> models) {
        Set<Long> termIds = models
            .stream()
            .map(RoomModel::getTermId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> parentIdsWithNames = termService.getTermNameMapByIds(termIds);
        return newArrayList(
            models,
            model -> this.modelToResponseConverter.toRoomItemResponse(
                model,
                parentIdsWithNames.get(model.getTermId())
            )
        );
    }

    public List<AdminRoomBaseResponse> decorateRoomBaseModel(List<TermModel> models) {
        return newArrayList(models, this.modelToResponseConverter::toRoomBaseResponse);
    }

    public PaginationModel<AdminRoomResponse> decorateRoomModel(PaginationModel<RoomModel> roomModelPagination) {
        List<AdminRoomResponse> adminRoomResponses = decorateRoomItem(roomModelPagination.getItems());
        return PaginationModel.<AdminRoomResponse>builder()
            .items(adminRoomResponses)
            .totalPage(roomModelPagination.getTotalPage())
            .currentPage(roomModelPagination.getCurrentPage())
            .build();
    }
}
