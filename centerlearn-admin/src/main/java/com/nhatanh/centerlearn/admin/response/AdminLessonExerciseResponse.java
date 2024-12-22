package com.nhatanh.centerlearn.admin.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class AdminLessonExerciseResponse {
    private final long id;
    private final String title;
    private final float priority;
    private final List<AdminListExerciseResponse> exercises;
}
