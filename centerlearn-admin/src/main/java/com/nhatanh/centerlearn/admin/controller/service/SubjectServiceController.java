package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminSubjectModelDecorator;
import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.model.AddSubjectModel;
import com.nhatanh.centerlearn.admin.model.SaveSubjectModel;
import com.nhatanh.centerlearn.admin.model.SubjectModel;
import com.nhatanh.centerlearn.admin.response.AdminSubjectResponse;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SubjectServiceController {
    private final SubjectService subjectService;
    private final AdminSubjectModelDecorator adminSubjectModelDecorator;

    public void addSubject(AddSubjectModel addSubjectModel) {
        long subjectId = this.subjectService.addSubject(addSubjectModel);
        if (subjectId == 0) {
            throw new FailedCreationException("Failed to create subject");
        }
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
}
