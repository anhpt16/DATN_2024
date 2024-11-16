package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.controller.service.RoomServiceController;
import com.nhatanh.centerlearn.admin.request.SaveRoomRequest;
import com.nhatanh.centerlearn.admin.request.UpdateRoomResquest;
import com.nhatanh.centerlearn.admin.service.RoomService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class RoomValidator {
    private final FormValidator formValidator;
    private final RoomServiceController roomServiceController;
    private final RoomService roomService;

    public void validate(SaveRoomRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.validateBlank(request.getName())) {
            errors.put("Name", "Blank");
        }
        if (!formValidator.validateNumberPositive(request.getSlot())) {
            errors.put("Slot", "Invalid");
        }
        // Check Valid
        if (!formValidator.validateSpecialCharacter(request.getName())) {
            errors.put("Name", "Has Special Character");
        }

        if (request.getBase() > 0) {
            if (roomServiceController.getRoomsByTermType() != null) {
                boolean isExist = roomServiceController.getRoomsByTermType().stream()
                    .anyMatch(room -> room.getId() == request.getBase());
                if (!isExist) {
                    errors.put("Room", "is not exist");
                }
            }
            else {
                errors.put("Room", "Has not facility");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(long id, UpdateRoomResquest request) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.validateBlank(request.getName())) {
            errors.put("Name", "Blank");
        }
        if (formValidator.isNull(request.getSlot())) {
            errors.put("Slot", "Null");
        }
        if (!formValidator.validateNumberPositive(request.getSlot())) {
            errors.put("Slot", "Invalid");
        }
        // Check Valid
        if (roomService.getRoomById(id) == null) {
            errors.put("Room", "is not exist");
        }
        if (!formValidator.validateSpecialCharacter(request.getName())) {
            errors.put("Name", "Has Special Character");
        }

        if (request.getBase() > 0) {
            if (roomServiceController.getRoomsByTermType() != null) {
                boolean isExist = roomServiceController.getRoomsByTermType().stream()
                    .anyMatch(room -> room.getId() == request.getBase());
                if (!isExist) {
                    errors.put("Room", "is not exist");
                }
            }
            else {
                errors.put("Room", "Has not facility");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
