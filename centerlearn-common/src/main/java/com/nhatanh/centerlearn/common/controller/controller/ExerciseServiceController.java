package com.nhatanh.centerlearn.common.controller.controller;

import com.nhatanh.centerlearn.common.controller.decorator.ExerciseModelDecorator;
import com.nhatanh.centerlearn.common.model.AddExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.UpdateExerciseModel;
import com.nhatanh.centerlearn.common.response.ExerciseResponse;
import com.nhatanh.centerlearn.common.service.ExerciseService;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ExerciseServiceController {
    private final ExerciseService exerciseService;
    private final ExerciseModelDecorator exerciseModelDecorator;

    // Thêm một bài tập
    public void addExercise(AddExerciseModel model) {
        this.exerciseService.addExercise(model);
    }

    // Xe một bài tập
    public ExerciseResponse getExerciseById(long exerciseId) {
        ExerciseModel exerciseModel = this.exerciseService.getExerciseById(exerciseId);
        if (exerciseModel == null) {
            throw new HttpNotFoundException("Exercise Not Found");
        }
        return this.exerciseModelDecorator.decorateExerciseModel(exerciseModel);
    }

    // Sửa một bài tập
    public void updateExercise(UpdateExerciseModel model) {
        this.exerciseService.updateExercise(model);
    }
}
