package com.nhatanh.centerlearn.web.repo;

import com.nhatanh.centerlearn.common.entity.Lesson;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface LessonRepository extends EzyDatabaseRepository<Long, Lesson> {

    @EzyQuery("SELECT l FROM Lesson l WHERE l.id = ?0 AND l.creatorId = ?1")
    Lesson findLessonByIdAndCreatorId(long lessonId, long creatorId);
}
