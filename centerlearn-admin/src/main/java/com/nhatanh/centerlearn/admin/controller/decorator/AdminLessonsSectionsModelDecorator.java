package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.TextbookLessonModel;
import com.nhatanh.centerlearn.admin.response.AdminLessonSectionResponse;
import com.nhatanh.centerlearn.admin.response.AdminListSectionResponse;
import com.nhatanh.centerlearn.common.model.LessonModel;
import com.nhatanh.centerlearn.common.model.SectionModel;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
public class AdminLessonsSectionsModelDecorator {
    private final AdminModelToResponseConverter adminModelToResponseConverter;

    public List<AdminLessonSectionResponse> decorateLessonsSectionsModel(List<TextbookLessonModel> textbookLessonModels, List<LessonModel> lessonModels, Map<Long, List<SectionModel>> sectionsMapByLessonIds) {
        return lessonModels.stream()
            .map(lesson -> {
                List<AdminListSectionResponse> sections = sectionsMapByLessonIds.get(lesson.getId()).stream()
                    .map(this.adminModelToResponseConverter::toListSectionResponse)
                    .collect(Collectors.toList());
                Optional<Float> lessonPriority = textbookLessonModels.stream()
                    .filter(model -> model.getLessonId() == lesson.getId())
                    .map(TextbookLessonModel::getPriority)
                    .findFirst();
                float priority = lessonPriority.orElse(0.0f);
                return this.adminModelToResponseConverter.toAdminLessonSectionResponse(lesson, sections, priority);
            })
            .collect(Collectors.toList());
    }
}
