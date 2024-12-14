package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface SubjectRepositoryCustom {
    List<Subject> findSubjectByCriteria(SubjectFilterCriteria criteria, Next next);
    long countSubjectByCriteria(SubjectFilterCriteria criteria);
}
