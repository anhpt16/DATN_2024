package com.nhatanh.centerlearn.admin.controller.decorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.model.CourseSubjectModel;
import com.nhatanh.centerlearn.admin.response.AdminCourseSubjectResponse;
import com.nhatanh.centerlearn.admin.service.SubjectService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@EzySingleton
@AllArgsConstructor
public class AdminCourseSubjectModelDecorator {
    private final AdminModelToResponseConverter adminModelToResponseConverter;
    private final SubjectService subjectService;
    private final TextbookService textbookService;

    public List<AdminCourseSubjectResponse> decorateCourseSubjectModel(List<CourseSubjectModel> courseSubjectModels) {
        Set<Long> subjectIds = courseSubjectModels.stream()
            .map(CourseSubjectModel::getSubjectId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Set<Long> textbookIds = courseSubjectModels.stream()
            .map(CourseSubjectModel::getTextbookId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        Map<Long, String> subjectNameMapByIds = this.subjectService.getSubjectNameMapByIds(subjectIds);
        Map<Long, String> textbookNameMapByIds = this.textbookService.getTextbookNameMapByIds(textbookIds);
        return newArrayList(courseSubjectModels, courseSubjectModel -> this.adminModelToResponseConverter.toCourseSubjectResponse(
            courseSubjectModel,
            subjectNameMapByIds.get(courseSubjectModel.getSubjectId()),
            textbookNameMapByIds.get(courseSubjectModel.getTextbookId())
        ));
    }
}
