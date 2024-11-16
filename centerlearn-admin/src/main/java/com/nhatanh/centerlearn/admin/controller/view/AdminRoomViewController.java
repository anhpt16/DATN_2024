package com.nhatanh.centerlearn.admin.controller.view;


import com.nhatanh.centerlearn.admin.controller.service.RoomServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TimeslotServiceController;
import com.nhatanh.centerlearn.admin.response.AdminRoomBaseResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.response.AdminTimeslotResponse;
import com.nhatanh.centerlearn.admin.service.RoomService;
import com.nhatanh.centerlearn.admin.service.TimeslotService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/room")
public class AdminRoomViewController {
    private final RoomServiceController roomServiceController;
    private final RoomService roomService;
    private final TimeslotService timeslotService;
    private final TimeslotServiceController timeslotServiceController;

    @DoGet("/")
    public View initRoom(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        List<AdminRoomBaseResponse> facilities = roomServiceController.getRoomsByTermType();
        PaginationModel<AdminRoomResponse> rooms = roomServiceController.getAllRoomPagination(page, size);
        List<String> roomStatus = roomService.getRoomStatus();
        return View.builder()
            .addVariable("facilities", facilities)
            .addVariable("rooms", rooms)
            .addVariable("roomStatus", roomStatus)
            .template("/contents/room/room")
            .build();
    }

    @DoGet("/timeslot")
    public View initTimeslot(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminTimeslotResponse> timeslots = timeslotServiceController.getAllTimeslot(page, size);
        List<String> timeslotStatuses = timeslotService.getTimeslotStatus();
        return View.builder()
            .addVariable("timeslots", timeslots)
            .addVariable("statuses", timeslotStatuses)
            .template("/contents/room/room-timeslot")
            .build();
    }

    @DoGet("/manage-timeslot")
    public View initManageTimeslot() {
        return View.builder()
            .template("/contents/room/room-timeslot-room")
            .build();
    }
}
