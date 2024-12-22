package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.LessonExercise;
import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

import java.util.List;

@EzyRepository
public interface LessonExerciseRepository extends EzyDatabaseRepository<LessonExerciseId, LessonExercise> {


    @EzyQuery("SELECT l FROM LessonExercise l WHERE l.lessonId = ?0 ORDER BY l.priority ASC ")
    List<LessonExercise> findListByLessonId(long lessonId);
}
