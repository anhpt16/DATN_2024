package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.model.SaveTimeslotModel;
import com.nhatanh.centerlearn.admin.request.SaveRoleRequest;
import com.nhatanh.centerlearn.admin.request.UpdateRoleRequest;
import com.nhatanh.centerlearn.admin.service.RoleService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class RoleValidator {
    private final FormValidator formValidator;
    private final RoleService roleService;

    public void validate(SaveRoleRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check blank
        if(formValidator.validateBlank(request.getName())) {
            errors.put("Name", "Blank");
        }
        if (formValidator.validateBlank(request.getDisplayName())) {
            errors.put("DisplayName", "Blank");
        }
        // check invalid
        if (!formValidator.validateSpecialCharacter(request.getName())) {
            errors.put("Name", "Has Special Character");
        }
        if (!formValidator.validateSpecialCharacter(request.getDisplayName())) {
            errors.put("DisplayName", "Has Special Character");
        }
        if (roleService.getRoleByName(request.getName().toUpperCase()) != null) {
            errors.put("Name", "Exist");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
    public void validate(UpdateRoleRequest request, long id) {
        Map<String, String> errors = new HashMap<>();
        // Check blank
        if (formValidator.validateBlank(request.getDisplayName())) {
            errors.put("DisplayName", "Blank");
        }
        // check invalid
        if (!formValidator.validateSpecialCharacter(request.getDisplayName())) {
            errors.put("DisplayName", "Has Special Character");
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
