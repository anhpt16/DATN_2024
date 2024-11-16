package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.TimeslotServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.model.TimeslotModel;
import com.nhatanh.centerlearn.admin.request.SaveTimeslotRequest;
import com.nhatanh.centerlearn.admin.request.UpdateTimeslotRequest;
import com.nhatanh.centerlearn.admin.response.AdminTimeslotResponse;
import com.nhatanh.centerlearn.admin.service.TimeslotService;
import com.nhatanh.centerlearn.admin.validator.TimeslotValidator;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;


@Api
@Controller("/api/v1/timeslots")
@AllArgsConstructor
public class AdminTimeslotApiController {
    private final TimeslotServiceController timeslotServiceController;
    private final TimeslotValidator timeslotValidator;
    private final TimeslotService timeslotService;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoPost("/")
    public ResponseEntity addTimeslot(
        @RequestBody SaveTimeslotRequest request
    ) {
        System.out.println(request.toString());
        this.timeslotValidator.validate(request);
        this.timeslotService.addTimeslot(this.requestToModelConverter.toSaveTimeslotModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoPut("/{id}")
    public ResponseEntity updateTimeslot(
        @PathVariable long id,
        @RequestBody UpdateTimeslotRequest request
    ) {
        System.out.println(id);
        System.out.println(request);
        this.timeslotValidator.validate(id, request);
        this.timeslotService.updateTimeslotById(id, this.requestToModelConverter.toSaveTimeslotModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoDelete("/{id}")
    public ResponseEntity deleteTimeslot(
        @PathVariable int id
    ) {
        System.out.println(id);
        this.timeslotService.deleteTimeslotById(id);
        return ResponseEntity.noContent();
    }

    @DoGet("/by-type")
    public PaginationModel<AdminTimeslotResponse> getTimeslotByType(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminTimeslotResponse> timeslotResponsePagination = timeslotServiceController.getAllTimeslot(page, size);
        return timeslotResponsePagination;
    }
}
