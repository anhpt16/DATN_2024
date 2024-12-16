package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminTextbookModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AddTextbookModel;
import com.nhatanh.centerlearn.admin.model.SaveTextbookModel;
import com.nhatanh.centerlearn.admin.model.SubjectTextbookModel;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.admin.service.SubjectTextbookService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class TextbookServiceController {
    private final TextbookService textbookService;
    private final SubjectTextbookService subjectTextbookService;
    private final AdminModelToResponseConverter adminModelToResponseConverter;
    private final AdminModelToModelConverter adminModelToModelConverter;
    private final AdminTextbookModelDecorator adminTextbookModelDecorator;

    public void addTextbook(AddTextbookModel addTextbookModel) {
        long textbookId = this.textbookService.addTextbook(addTextbookModel);
        if (textbookId == 0) {
            throw new FailedCreationException("Fail to create Textbook");
        }
        // Save TextbookSubject
        if (addTextbookModel.getSubjectId() > 0) {
            SubjectTextbookModel subjectTextbookModel = this.adminModelToModelConverter.toSubjectTextbookModelConverter(addTextbookModel.getSubjectId(), textbookId);
            SubjectTextbookId subjectTextbookId = this.subjectTextbookService.addSubjectTextbook(subjectTextbookModel);
            if (subjectTextbookId == null) {
                throw new FailedCreationException("Fail to create SubjectTextbook");
            }
        }
    }

    public PaginationModel<AdminTextbookResponse> getTextbookPaginationByFilter(TextbookFilterCriteria criteria, int page, int size) {
        PaginationModel<TextbookModel> textbookPaginationModel = this.textbookService.getTextbookPaginationByFilter(criteria, page, size);
        return this.adminTextbookModelDecorator.decorateTextbookPaginationModel(textbookPaginationModel);
    }

    public AdminTextbookResponse getTextbookById(long id) {
        TextbookModel textbookModel = this.textbookService.getTextBookById(id);
        if (textbookModel == null) {
            throw new HttpNotFoundException("Textbook with id: " + id + " invalid");
        }
        return this.adminModelToResponseConverter.toTextbookResponse(textbookModel);
    }

    public void updatedTextbookById(SaveTextbookModel model) {
        this.textbookService.updateTextbook(model);
    }

    public List<TextbookStatus> getAllTextbookStatus() {
        List<TextbookStatus> textbookStatuses = Arrays.asList(TextbookStatus.values());
        return textbookStatuses;
    }
}
