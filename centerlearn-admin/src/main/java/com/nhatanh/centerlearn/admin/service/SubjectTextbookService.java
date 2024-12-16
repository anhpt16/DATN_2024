package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.SubjectTextbookModel;
import com.nhatanh.centerlearn.admin.repo.SubjectTextbookRepository;
import com.nhatanh.centerlearn.common.entity.SubjectTextbook;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubjectTextbookService {
    private final SubjectTextbookRepository subjectTextbookRepository;
    private final AdminModelToEntityConverter adminModelToEntityConverter;

    public SubjectTextbookId addSubjectTextbook(SubjectTextbookModel subjectTextbookModel) {
        SubjectTextbook subjectTextbook = this.adminModelToEntityConverter.toSubjectTextbookEntityConverter(subjectTextbookModel);
        this.subjectTextbookRepository.save(subjectTextbook);
        return new SubjectTextbookId(
            subjectTextbook.getSubjectId(),
            subjectTextbook.getTextbookId()
        );
    }
}
