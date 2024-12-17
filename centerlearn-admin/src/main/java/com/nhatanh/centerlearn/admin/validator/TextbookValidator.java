package com.nhatanh.centerlearn.admin.validator;

import com.nhatanh.centerlearn.admin.controller.service.SubjectServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddTextbookRequest;
import com.nhatanh.centerlearn.admin.request.SaveTextbookRequest;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@EzySingleton
public class TextbookValidator {
    private final TextbookServiceController textbookServiceController;
    private final SubjectService subjectService;
    private final FormValidator formValidator;
    private TextbookService textbookService;

    public void validate(AddTextbookRequest request) {
        List<String> errors = new ArrayList<>();
        // validate name
        if (request.getName() == null) {
            errors.add("Name Invalid");
            if (formValidator.validateBlank(request.getName())) {
                errors.add("Name is blank");
            }
        }
        // validate author
        if (request.getAuthor() == null) {
            errors.add("Author Invalid");
            if (formValidator.validateBlank(request.getAuthor())) {
                errors.add("Author is blank");
            }
        }
        // validate subjectId
        if (request.getSubjectId() > 0) {
            if (this.subjectService.getSubjectById(request.getSubjectId()) == null) {
                errors.add("Subject with id: " + request.getSubjectId() + " not found");
            }

        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validate(SaveTextbookRequest request, long textbookId) {
        List<String> errors = new ArrayList<>();
        // validate id
        if (textbookId == 0) {
            errors.add("TextbookId is blank");
        } else {
            if (this.textbookServiceController.getTextbookById(textbookId) == null) {
                errors.add("Textbook with id: " + textbookId + " invalid");
            }
        }
        // validate name
        if (request.getName() != null) {
            if (formValidator.validateBlank(request.getName())) {
                errors.add("Name is blank");
            }
        }
        // validate author
        if (request.getAuthor() != null) {
            if (formValidator.validateBlank(request.getAuthor())) {
                errors.add("Author is blank");
            }
        }
        // validate status
        if (request.getStatus() != null) {
            if (TextbookStatus.fromString(request.getStatus()) == null) {
                errors.add("Status does not exist");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }

    public void validateNull(SaveTextbookRequest request) {
        if (request.getName() == null
            && request.getDescription() == null
            && request.getAuthor() == null
            && request.getUrl() == null
            && request.getStatus() == null
        ) {
            throw new HttpBadRequestException("No thing to Update");
        }
    }

    public void validate(TextbookFilterCriteria criteria) {
        List<String> errors = new ArrayList<>();
        // Kiểm tra status tồn tại
        if (criteria.getStatus() != null) {
            if (TextbookStatus.fromString(criteria.getStatus()) == null) {
                errors.add("Status does not exist");
            } else {
                criteria.setStatus(criteria.getStatus().toUpperCase());
            }
        }
        // Kiểm tra subjectId
        if (criteria.getSubjectId() > 0) {
            if (this.subjectService.getSubjectById(criteria.getSubjectId()) == null) {
                errors.add("Subject with id: " + criteria.getSubjectId() + " not found");
            }
        }
        if (errors.size() > 0) {
            throw new HttpBadRequestException(errors);
        }
    }
}
