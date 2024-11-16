package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminRoomModelDecorator;
import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.model.TermModel;
import com.nhatanh.centerlearn.admin.response.AdminRoomBaseResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.service.RoomService;
import com.nhatanh.centerlearn.admin.service.TermService;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.entity.Term;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import javafx.scene.control.Pagination;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class RoomServiceController {
    private final TermService termService;
    private final RoomService roomService;
    private final AdminRoomModelDecorator roomModelDecorator;

    public List<AdminRoomBaseResponse> getRoomsByTermType() {
        List<TermModel> models = termService.getTermsByType(Constants.ROOM_TERM);
        return models != null ? this.roomModelDecorator.decorateRoomBaseModel(models) : null;
    }

    public PaginationModel<AdminRoomResponse> getAllRoomPagination(int page, int size) {
        PaginationModel<RoomModel> models = roomService.getAllRoomPagination(page, size);
        return this.roomModelDecorator.decorateRoomModel(models);
    }

    public PaginationModel<AdminRoomResponse> getRoomByTermIdPagination(long termId, int page, int size) {
        PaginationModel<RoomModel> models = roomService.getRoomsByTermId(termId, page, size);
        return this.roomModelDecorator.decorateRoomModel(models);
    }
}
