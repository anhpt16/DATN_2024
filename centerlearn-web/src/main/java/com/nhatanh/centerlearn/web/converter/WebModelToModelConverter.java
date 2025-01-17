package com.nhatanh.centerlearn.web.converter;

import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class WebModelToModelConverter {
    public ExerciseModelWithPriority toExerciseModelWithPriority(ExerciseModel exerciseModel, float priority) {
        return ExerciseModelWithPriority.builder()
            .id(exerciseModel.getId())
            .title(exerciseModel.getTitle())
            .content(exerciseModel.getContent())
            .creatorId(exerciseModel.getCreatorId())
            .status(exerciseModel.getStatus())
            .createdAt(exerciseModel.getCreatedAt())
            .updatedAt(exerciseModel.getUpdatedAt())
            .userTermId(exerciseModel.getUserTermId())
            .priority(priority)
            .build();
    }
}
