package com.nhatanh.centerlearn.common.repository;

import com.nhatanh.centerlearn.common.entity.Exercise;
import com.tvd12.ezydata.database.EzyDatabaseRepository;
import com.tvd12.ezyfox.database.annotation.EzyQuery;
import com.tvd12.ezyfox.database.annotation.EzyRepository;

@EzyRepository
public interface ExerciseRepository extends EzyDatabaseRepository<Long, Exercise> {

    @EzyQuery("SELECT e FROM Exercise e WHERE e.id = ?0 AND e.creatorId = ?1 ")
    Exercise findByIdAndCreatorId(long exerciseId, long creatorId);
}
