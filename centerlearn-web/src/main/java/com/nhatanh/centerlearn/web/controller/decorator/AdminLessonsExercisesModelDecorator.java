package com.nhatanh.centerlearn.web.controller.decorator;


import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.nhatanh.centerlearn.web.converter.WebModelToResponseConverter;
import com.nhatanh.centerlearn.web.model.TextbookLessonModel;
import com.nhatanh.centerlearn.web.response.AdminLessonExerciseResponse;
import com.nhatanh.centerlearn.web.response.AdminListExerciseResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class AdminLessonsExercisesModelDecorator {
    private final WebModelToResponseConverter adminModelToResponseConverter;

    public List<AdminLessonExerciseResponse> decorateAdminLessonExerciseModel(List<TextbookLessonModel> textbookLessonModels, List<LessonModel> lessonModels, Map<Long, List<ExerciseModelWithPriority>> exercisesMapByLessonIds) {
        return lessonModels.stream()
            .map(lesson -> {
                List<AdminListExerciseResponse> exercices = exercisesMapByLessonIds.get(lesson.getId()).stream()
                    .map(this.adminModelToResponseConverter::toListExerciseResponse)
                    .collect(Collectors.toList());
                Optional<Float> lessonPriority = textbookLessonModels.stream()
                    .filter(model -> model.getLessonId() == lesson.getId())
                    .map(TextbookLessonModel::getPriority)
                    .findFirst();
                float priority = lessonPriority.orElse(0.0f);
                return this.adminModelToResponseConverter.toAdminLessonExerciseResponse(lesson, exercices, priority);
            })
            .collect(Collectors.toList());
    }
}
