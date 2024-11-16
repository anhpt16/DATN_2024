package com.nhatanh.centerlearn.admin.test.service;

import com.nhatanh.centerlearn.admin.model.RoomModel;
import com.nhatanh.centerlearn.admin.response.AdminRoomResponse;
import com.nhatanh.centerlearn.admin.service.RoomService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.bean.EzyBeanConfig;
import com.tvd12.ezyfox.bean.annotation.EzyConfigurationAfter;
import lombok.AllArgsConstructor;

import java.util.List;

@EzyConfigurationAfter
@AllArgsConstructor
public class RoomServiceTest implements EzyBeanConfig {
    private final RoomService roomService;


    @Override
    public void config() {

        //when
        PaginationModel<RoomModel> getAllRoomPagination = roomService.getAllRoomPagination(0, 10);
        List<String> statuses = roomService.getRoomStatus();
        PaginationModel<RoomModel> roomEmptyTerms = roomService.getRoomsByTermId(0,0, 10);
        //then
    }
}
