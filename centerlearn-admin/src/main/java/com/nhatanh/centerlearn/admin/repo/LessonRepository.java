package com.nhatanh.centerlearn.admin.repo;

import com.nhatanh.centerlearn.common.entity.Lesson;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface LessonRepository extends EzyDatabaseRepository<Long, Lesson> {

}
