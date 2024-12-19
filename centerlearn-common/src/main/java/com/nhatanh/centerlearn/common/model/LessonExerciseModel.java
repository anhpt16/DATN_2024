package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LessonExerciseModel {
    private final long lessonId;
    private final long exerciseId;
    private final float priority;
}
