package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.SubjectTextbook;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface SubjectTextbookRepository extends EzyDatabaseRepository<SubjectTextbookId, SubjectTextbook> {

}
