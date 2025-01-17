package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Subject;
import com.nhatanh.centerlearn.common.result.IdResult;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface SubjectRepository extends EzyDatabaseRepository<Long, Subject> {

    @EzyQuery("SELECT s.id FROM Subject s WHERE s.name = ?0")
    IdResult findSubjectIdByName(String name);

}
