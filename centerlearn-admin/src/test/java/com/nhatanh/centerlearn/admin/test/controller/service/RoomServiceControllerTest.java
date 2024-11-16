package com.nhatanh.centerlearn.admin.test.controller.service;

import com.nhatanh.centerlearn.admin.controller.service.RoomServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TimeslotServiceController;
import com.nhatanh.centerlearn.admin.response.AdminRoomBaseResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.response.AdminTimeslotResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class RoomServiceControllerTest implements EzyBeanConfig {
    private final RoomServiceController roomServiceController;
    private final TimeslotServiceController timeslotServiceController;
    @Override
    public void config() {

        //when
        List<AdminRoomBaseResponse> roomBases = roomServiceController.getRoomsByTermType();
        PaginationModel<AdminRoomResponse> roomPagination = roomServiceController.getAllRoomPagination(0, 10);
        PaginationModel<AdminRoomResponse> roomByTerms = roomServiceController.getRoomByTermIdPagination(31, 0, 10);
        PaginationModel<AdminRoomResponse> roomEmptyTerms = roomServiceController.getRoomByTermIdPagination(0, 0, 10);
        PaginationModel<AdminTimeslotResponse> timeslots = timeslotServiceController.getAllTimeslot(0, 10);
        //then
    }
}
