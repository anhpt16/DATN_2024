package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.request.SaveTermRequest;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@EzySingleton
public class TermValidator {
    private final TermServiceController termServiceController;
    private final FormValidator formValidator;

    public void validate(SaveTermRequest request) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.validateBlank(request.getName())) {
            errors.put("TermName", "Blank");
        }
        if (formValidator.validateBlank(request.getTermType())) {
            errors.put("TermType", "Blank");
        }
        // Check Valid
        if (!formValidator.validateSpecialCharacter(request.getName())) {
            errors.put("Name", "Has Special Character");
        }
        if (!formValidator.validateSpecialCharacter(request.getTermType())) {
            errors.put("Type", "Has Special Character");
        }
        if (termServiceController.getIdByTermNameAndType(request.getName().toUpperCase(), request.getTermType()) > 0) {
            errors.put("Term", "Exist");
        }
        if (!formValidator.validateBlank(request.getParentName())) {
            if (!formValidator.validateBlank(request.getParentType())) {
                if(termServiceController.getIdByTermNameAndType(request.getParentName().toUpperCase(), request.getParentType()) == 0) {
                    errors.put("Parent", "Invalid");
                }
            }
            else {
                errors.put("ParentType", "Blank");
            }
        }
        else {
            if (!formValidator.validateBlank(request.getParentType())) {
                errors.put("ParentType", "No Parent Name");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(SaveTermRequest request, long id) {
        Map<String, String> errors = new HashMap<>();
        // Check Blank
        if (formValidator.validateBlank(request.getName())) {
            errors.put("TermName", "Blank");
        }
        if (formValidator.validateBlank(request.getTermType())) {
            errors.put("TermType", "Blank");
        }
        // Check Valid
        if (!formValidator.validateSpecialCharacter(request.getName())) {
            errors.put("Name", "Has Special Character");
        }
        if (!formValidator.validateSpecialCharacter(request.getTermType())) {
            errors.put("Type", "Has Special Character");
        }
        if (
            termServiceController.getIdByTermNameAndType(request.getName().toUpperCase(), request.getTermType()) > 0
            && termServiceController.getIdByTermNameAndType(request.getName().toUpperCase(), request.getTermType()) != id
        )
        {
            errors.put("Term", "Exist");
        }
        if (!formValidator.validateBlank(request.getParentName())) {
            if (!formValidator.validateBlank(request.getParentType())) {
                if(termServiceController.getIdByTermNameAndType(request.getParentName().toUpperCase(), request.getParentType()) == 0) {
                    errors.put("Parent", "Invalid");
                }
            }
            else {
                errors.put("ParentType", "Blank");
            }
        }
        else {
            if (!formValidator.validateBlank(request.getParentType())) {
                errors.put("ParentType", "No Parent Name");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
