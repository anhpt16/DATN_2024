package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.converter.AdminEntityToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToEntityConverter;
import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.repo.SubjectRepository;
import com.nhatanh.centerlearn.admin.repo.SubjectRepositoryCustom;
import com.nhatanh.centerlearn.admin.response.AdminSubjectShortResponse;
import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Account;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.entity.Term;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.ezyfox.util.Next;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final AdminModelToEntityConverter adminModelToEntityConverter;
    private final AdminEntityToModelConverter adminEntityToModelConverter;
    private final SubjectRepositoryCustom subjectRepositoryCustom;

    public long getSubjectIdByName(String name) {
        IdResult result = this.subjectRepository.findSubjectIdByName(name);
        return result == null ? 0L : result.getId();
    }

    public SubjectModel getSubjectById(long subjectId) {
        Subject subject = this.subjectRepository.findById(subjectId);
        return subject == null ? null : this.adminEntityToModelConverter.toModel(subject);
    }

    public long addSubject(AddSubjectModel model) {
        Subject subject = this.adminModelToEntityConverter.toSubjectEntityConverter(model);
        this.subjectRepository.save(subject);
        return subject.getId();
    }

    public List<SubjectModel> getAllSubject() {
        List<Subject> subjectModels = this.subjectRepository.findAll();
        return newArrayList(subjectModels, this.adminEntityToModelConverter::toModel);
    }

    public void updateSubject(SaveSubjectModel model) {
        Subject subject = this.subjectRepository.findById(model.getId());
        if (subject == null) {
            throw new ResourceNotFoundException("Subject");
        }
        this.adminModelToEntityConverter.mergeToSaveEntity(subject, model);
        this.subjectRepository.save(subject);
    }

    public PaginationModel<SubjectModel> getSubjectPaginationByFilter(SubjectFilterCriteria criteria, int page, int size) {
        long totalPage = (long) Math.ceil((double) this.subjectRepositoryCustom.countSubjectByCriteria(criteria) / size);
        if (page > totalPage) {
            throw new ResourceNotFoundException("page", "invalid");
        }
        List<SubjectModel> subjectModels = newArrayList(this.subjectRepositoryCustom.findSubjectByCriteria(criteria, Next.fromPageSize(page, size)), adminEntityToModelConverter::toModel);
        return PaginationModel.<SubjectModel>builder()
            .items(subjectModels)
            .currentPage(page)
            .totalPage(totalPage)
            .build();
    }

    public Map<Long, String> getSubjectNameMapByIds(Collection<Long> ids) {
        return EzyMaps.newHashMapNewValues(this.getSubjectMapByIds(ids), SubjectModel::getDisplayName);
    }

    public Map<Long, SubjectModel> getSubjectMapByIds(Collection<Long> ids) {
        return ids.isEmpty() ? Collections.emptyMap() : this.subjectRepository.findListByIds(ids).stream()
            .collect(Collectors.toMap(Subject::getId, this.adminEntityToModelConverter::toModel, (o, n) -> n));
    }
}
