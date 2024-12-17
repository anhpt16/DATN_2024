package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.model.SubjectTextbookModel;
import com.nhatanh.centerlearn.admin.repo.SubjectTextbookRepository;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.SubjectTextbook;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class SubjectTextbookService {
    private final SubjectTextbookRepository subjectTextbookRepository;
    private final AdminModelToEntityConverter adminModelToEntityConverter;
    private final AdminEntityToModelConverter adminEntityToModelConverter;

    public SubjectTextbookId addSubjectTextbook(SubjectTextbookModel subjectTextbookModel) {
        SubjectTextbook subjectTextbook = this.adminModelToEntityConverter.toSubjectTextbookEntityConverter(subjectTextbookModel);
        this.subjectTextbookRepository.save(subjectTextbook);
        if (subjectTextbook == null) {
            throw new HttpNotFoundException("Fail To Create SubjectTextbook");
        }
        return new SubjectTextbookId(
            subjectTextbook.getSubjectId(),
            subjectTextbook.getTextbookId()
        );
    }

    public SubjectTextbookModel getSubjectTextbookById(SubjectTextbookId id) {
        SubjectTextbook subjectTextbook = this.subjectTextbookRepository.findById(id);
        return subjectTextbook == null ? null : this.adminEntityToModelConverter.toSubjectTextbookModel(subjectTextbook);
    }

    public void deleteSubjectTextbookById(SubjectTextbookId id) {
        SubjectTextbook subjectTextbook = this.subjectTextbookRepository.findById(id);
        if (subjectTextbook == null) {
            throw new HttpNotFoundException("Subject - Textbook Not Found");
        }
        this.subjectTextbookRepository.delete(id);
    }

    public List<Long> getTextbookIdsBySubjectId(long subjectId) {
        List<IdResult> ids = this.subjectTextbookRepository.findTextbookIdsBySubjectId(subjectId);
        return ids.isEmpty() ? Collections.emptyList() : newArrayList(ids, IdResult::getId);
    }
}
