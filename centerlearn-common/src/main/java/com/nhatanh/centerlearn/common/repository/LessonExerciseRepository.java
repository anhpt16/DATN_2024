package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.LessonExercise;
import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface LessonExerciseRepository extends EzyDatabaseRepository<LessonExerciseId, LessonExercise> {

}
