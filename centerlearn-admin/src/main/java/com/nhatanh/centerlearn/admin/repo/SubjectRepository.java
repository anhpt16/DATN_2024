package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.admin.result.IdResult;
import com.nhatanh.centerlearn.common.entity.Subject;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface SubjectRepository extends EzyDatabaseRepository<Long, Subject> {

    @EzyQuery("SELECT s.id FROM Subject s WHERE s.name = ?0")
    IdResult findSubjectIdByName(String name);

}
