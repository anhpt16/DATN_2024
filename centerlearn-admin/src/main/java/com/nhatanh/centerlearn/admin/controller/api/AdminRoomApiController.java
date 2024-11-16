package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.RoomServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.request.SaveRoomRequest;
import com.nhatanh.centerlearn.admin.request.UpdateRoomResquest;
import com.nhatanh.centerlearn.admin.response.AdminRoomBaseResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.service.RoomService;
import com.nhatanh.centerlearn.admin.validator.FormValidator;
import com.nhatanh.centerlearn.admin.validator.RoomValidator;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

@Api
@Controller("/api/v1/rooms")
@AllArgsConstructor
public class AdminRoomApiController {
    private final AdminRequestToModelConverter requestToModelConverter;
    private final RoomValidator roomValidator;
    private final RoomServiceController roomServiceController;
    private final RoomService roomService;
    private final FormValidator formValidator;

    @DoPost("/")
    public ResponseEntity addRoom(
        @RequestBody SaveRoomRequest request
    ) {
        System.out.println(request.toString());
        this.roomValidator.validate(request);
        this.roomService.addRoom(this.requestToModelConverter.toSaveRoomModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoGet("/facility")
    public List<AdminRoomBaseResponse> getRoomFacility() {
        List<AdminRoomBaseResponse> facilities = roomServiceController.getRoomsByTermType();
        if (facilities == null) {
            return Collections.emptyList();
        }
        return facilities;
    }

    @DoGet("/by-term-id")
    public PaginationModel<AdminRoomResponse> getRoomByTermId(
        @RequestParam (value = "id") long id,
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        System.out.println(id);
        PaginationModel<AdminRoomResponse> adminRoomResponses = formValidator.validateNumberPositive(id)
            ? roomServiceController.getRoomByTermIdPagination(id, page, size)
            : roomServiceController.getAllRoomPagination(page, size);
        return adminRoomResponses;
    }

    @DoGet("/{id}/term-id")
    public long getTermIdByRoomId(
        @PathVariable long id
    ) {
        long termId = roomService.getTermIdByRoomId(id);
        return termId;
    }

    @DoPut("/{id}")
    public ResponseEntity updatedRoomById(
        @PathVariable long id,
        @RequestBody UpdateRoomResquest request
    ) {
        System.out.println(id);
        System.out.println(request.toString());
        this.roomValidator.validate(id, request);
        this.roomService.updateRoomById(id, this.requestToModelConverter.toSaveRoomModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoDelete("/{id}")
    public ResponseEntity deleteRoomById(
        @PathVariable long id
    ) {
        System.out.println(id);
        this.roomService.deleteRoomById(id);
        return ResponseEntity.noContent();
    }
}
