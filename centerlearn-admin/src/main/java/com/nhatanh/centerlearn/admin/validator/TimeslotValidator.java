package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.model.SaveTimeslotModel;
import com.nhatanh.centerlearn.admin.request.SaveTimeslotRequest;
import com.nhatanh.centerlearn.admin.request.UpdateTimeslotRequest;
import com.nhatanh.centerlearn.admin.service.TimeslotService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class TimeslotValidator {
    private final FormValidator formValidator;
    private final TimeslotService timeslotService;

    public void validate(SaveTimeslotRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.isNull(request.getTimeStart())) {
            errors.put("TimeStart", "Null");
        }
        if (formValidator.isNull(request.getTimeEnd())) {
            errors.put("TimeEnd", "Null");
        }
        if (formValidator.isNull(request.getPeriod())) {
            errors.put("Period", "Null");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(long id, UpdateTimeslotRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.isNull(request.getTimeStart())) {
            errors.put("TimeStart", "Null");
        }
        if (formValidator.isNull(request.getTimeEnd())) {
            errors.put("TimeEnd", "Null");
        }
        if (formValidator.isNull(request.getPeriod())) {
            errors.put("Period", "Null");
        }
        // Check valid
        if (timeslotService.getTimeslotById(id) == null) {
            errors.put("Timeslot", "invalid");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
