package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TextbookLessonModel;
import com.nhatanh.centerlearn.admin.response.AdminLessonExerciseResponse;
import com.nhatanh.centerlearn.admin.response.AdminListExerciseResponse;
import com.nhatanh.centerlearn.admin.response.AdminListSectionResponse;
import com.nhatanh.centerlearn.common.model.ExerciseModel;
import com.nhatanh.centerlearn.common.model.ExerciseModelWithPriority;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class AdminLessonsExercisesModelDecorator {
    private final AdminModelToResponseConverter adminModelToResponseConverter;

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
