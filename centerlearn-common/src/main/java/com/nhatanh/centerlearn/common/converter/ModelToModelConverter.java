package com.nhatanh.centerlearn.common.converter;

import com.nhatanh.centerlearn.common.model.LessonExerciseModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

@EzySingleton
@AllArgsConstructor
public class ModelToModelConverter {

    public LessonExerciseModel toLessonExerciseModel(long lessonId, long exerciseId, float priority) {
        return LessonExerciseModel.builder()
            .lessonId(lessonId)
            .exerciseId(exerciseId)
            .priority(priority)
            .build();
    }
}
