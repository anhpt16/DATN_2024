package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminTimeslotModelDecorator;
import com.nhatanh.centerlearn.admin.model.TimeslotModel;
import com.nhatanh.centerlearn.admin.response.AdminTimeslotResponse;
import com.nhatanh.centerlearn.admin.service.TimeslotService;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TimeslotServiceController {
    private final TimeslotService timeslotService;
    private final AdminTimeslotModelDecorator timeslotModelDecorator;

    public PaginationModel<AdminTimeslotResponse> getAllTimeslot(int page, int size) {
        PaginationModel<TimeslotModel> timeslotModelPagination = timeslotService.getAllTimeSlot(page, size);
        return this.timeslotModelDecorator.decorateTimeslotModel(timeslotModelPagination);
    }
}
