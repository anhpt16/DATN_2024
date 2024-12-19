package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Exercise;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.AddExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.UpdateExerciseModel;
import com.nhatanh.centerlearn.common.repository.ExerciseRepository;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    private final ModelToEntityConverter modelToEntityConverter;
    private final EntityToModelConverter entityToModelConverter;

    public long addExercise(AddExerciseModel model) {
        Exercise exercise = this.modelToEntityConverter.toExerciseEntityConverter(model);
        this.exerciseRepository.save(exercise);
        if (exercise.getId() == 0) {
            throw new FailedCreationException("Fail to create Exercise");
        }
        return exercise.getId();
    }

    public ExerciseModel getExerciseByIdAndCreatorId(long exerciseId, long creatorId) {
        Exercise exercise = this.exerciseRepository.findByIdAndCreatorId(exerciseId, creatorId);
        return exercise == null ? null : this.entityToModelConverter.toExerciseModel(exercise);
    }

    public ExerciseModel getExerciseById(long exerciseId) {
        Exercise exercise = this.exerciseRepository.findById(exerciseId);
        return exercise == null ? null : this.entityToModelConverter.toExerciseModel(exercise);
    }

    public void updateExercise(UpdateExerciseModel model) {
        Exercise exercise = this.exerciseRepository.findById(model.getId());
        if (exercise == null) {
            throw new HttpNotFoundException("Exercise Not Found");
        }
        this.modelToEntityConverter.mergeToSaveEntity(exercise, model);
        this.exerciseRepository.save(exercise);
    }
}
