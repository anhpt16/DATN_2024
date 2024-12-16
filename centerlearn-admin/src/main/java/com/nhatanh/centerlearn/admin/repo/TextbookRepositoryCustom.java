package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.entity.Textbook;
import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.util.Next;

import java.util.List;

@EzyRepository
public interface TextbookRepositoryCustom {
    List<Textbook> findTextbookByCriteria(TextbookFilterCriteria criteria, Next next);
    long countTextbookByCriteria(TextbookFilterCriteria criteria);
}
