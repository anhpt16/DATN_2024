package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.SubjectTextbook;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface SubjectTextbookRepository extends EzyDatabaseRepository<SubjectTextbookId, SubjectTextbook> {

    @EzyQuery("SELECT s.textbookId FROM SubjectTextbook s WHERE s.subjectId = ?0")
    List<IdResult> findTextbookIdsBySubjectId(long subjectId);
}
