package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminSubjectModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AddSubjectModel;
import com.nhatanh.centerlearn.admin.model.SaveSubjectModel;
import com.nhatanh.centerlearn.admin.model.SubjectModel;
import com.nhatanh.centerlearn.admin.model.TextbookModel;
import com.nhatanh.centerlearn.admin.response.AdminSubjectResponse;
import com.nhatanh.centerlearn.admin.response.AdminSubjectShortResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookShortResponse;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.admin.service.SubjectTextbookService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;


@Service
@AllArgsConstructor
public class SubjectServiceController {
    private final SubjectService subjectService;
    private final SubjectTextbookService subjectTextbookService;
    private final TextbookService textbookService;
    private final AdminSubjectModelDecorator adminSubjectModelDecorator;
    private final AdminModelToResponseConverter adminModelToResponseConverter;
    private final AdminModelToModelConverter adminModelToModelConverter;

    public void addSubject(AddSubjectModel addSubjectModel) {
        long subjectId = this.subjectService.addSubject(addSubjectModel);
        if (subjectId == 0) {
            throw new FailedCreationException("Failed to create subject");
        }
    }

    public List<AdminSubjectShortResponse> getAllSubjectShort() {
        List<SubjectModel> subjectModels = this.subjectService.getAllSubject();
        if (subjectModels.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(subjectModels, this.adminModelToResponseConverter::toSubjectShortResponse);
    }

    public List<SubjectStatus> getAllSubjectStatus() {
        List<SubjectStatus> subjectStatuses = Arrays.asList(SubjectStatus.values());
        return subjectStatuses;
    }

    public AdminSubjectResponse getSubjectById(long id) {
        SubjectModel subjectModel = this.subjectService.getSubjectById(id);
        if (subjectModel == null) {
            throw new HttpNotFoundException("Subject with id: " + id + " invalid");
        }
        return this.adminSubjectModelDecorator.decorateSubjectModel(subjectModel);
    }

    public void updateSubject(SaveSubjectModel saveSubjectModel) {
        this.subjectService.updateSubject(saveSubjectModel);
    }

    public PaginationModel<AdminSubjectResponse> getSubjectPaginationByFilter(SubjectFilterCriteria criteria, int page, int size) {
        PaginationModel<SubjectModel> subjectPaginationModel = this.subjectService.getSubjectPaginationByFilter(criteria, page, size);
        return this.adminSubjectModelDecorator.decorateSubjectPaginationModel(subjectPaginationModel);
    }

    public void addSubjectTextbookById(long subjectId, long textbookId) {
        this.subjectTextbookService.addSubjectTextbook(this.adminModelToModelConverter.toSubjectTextbookModelConverter(subjectId, textbookId));
    }

    public void deleteSubjectTextbookById(long subjectId, long textbookId) {
        SubjectTextbookId subjectTextbookId = new SubjectTextbookId(subjectId, textbookId);
        this.subjectTextbookService.deleteSubjectTextbookById(subjectTextbookId);
    }

    public List<AdminTextbookShortResponse> getTextbooksBySubjectId(long subjectId) {
        List<Long> textbookIds = this.subjectTextbookService.getTextbookIdsBySubjectId(subjectId);
        if (textbookIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<TextbookModel> textbookModels = this.textbookService.getTextbooksByIds(textbookIds);
        if (textbookModels.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(textbookModels, this.adminModelToResponseConverter::toTextbookShortResponse);
    }
}
