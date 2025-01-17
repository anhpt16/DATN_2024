package com.nhatanh.centerlearn.web.controller.service;

import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.service.ExerciseService;
import com.nhatanh.centerlearn.common.service.LessonExerciseService;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.nhatanh.centerlearn.web.controller.decorator.AdminLessonsExercisesModelDecorator;
import com.nhatanh.centerlearn.web.controller.decorator.AdminLessonsSectionsModelDecorator;
import com.nhatanh.centerlearn.web.converter.WebModelToModelConverter;
import com.nhatanh.centerlearn.web.converter.WebModelToResponseConverter;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.model.CourseSubjectModel;
import com.nhatanh.centerlearn.web.model.TextbookLessonModel;
import com.nhatanh.centerlearn.web.response.AdminLessonExerciseResponse;
import com.nhatanh.centerlearn.web.response.AdminLessonSectionResponse;
import com.nhatanh.centerlearn.web.response.AllCourseTextbookExercise;
import com.nhatanh.centerlearn.web.response.AllCourseTextbookSection;
import com.nhatanh.centerlearn.web.service.CourseService;
import com.nhatanh.centerlearn.web.service.CourseSubjectService;
import com.nhatanh.centerlearn.web.service.SubjectService;
import com.nhatanh.centerlearn.web.service.TextbookLessonService;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TextbookServiceController {
    private final CourseService courseService;
    private final SubjectService subjectService;
    private final CourseSubjectService courseSubjectService;
    private final TextbookLessonService textbookLessonService;
    private final LessonService lessonService;
    private final SectionService sectionService;
    private final AdminLessonsExercisesModelDecorator adminLessonsExercisesModelDecorator;
    private final AdminLessonsSectionsModelDecorator adminLessonsSectionsModelDecorator;
    private final LessonExerciseService lessonExerciseService;
    private final ExerciseService exerciseService;
    private final WebModelToModelConverter webModelToModelConverter;
    private final WebModelToResponseConverter webModelToResponseConverter;

    public List<AllCourseTextbookSection> getAllCourseWithTextbookSection(String slug) {
        CourseModel courseModel = this.courseService.getCourseBySlug(slug);
        List<CourseSubjectModel> courseSubjectModels = this.courseSubjectService.getCourseSubjectsByCourseId(courseModel.getId());
        Set<Long> subjectIds = courseSubjectModels.stream().map(CourseSubjectModel::getSubjectId).filter(id -> id > 0).collect(Collectors.toSet());
        Set<Long> textbookIds = courseSubjectModels.stream().map(CourseSubjectModel::getTextbookId).filter(id -> id > 0).collect(Collectors.toSet());
        Map<Long, String> subjectNameMapByIds = this.subjectService.getSubjectNameMapByIds(subjectIds);
        Map<Long, List<AdminLessonSectionResponse>> textbookContentMapByIds = textbookIds.stream().collect(Collectors.toMap(
            textbookId -> textbookId,
            textbookId -> this.getLessonsSectionsByTextbookId(textbookId).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        ));
        List<AllCourseTextbookSection> allCourseTextbookSections = courseSubjectModels.stream()
            .map(courseSubjectModel -> {
                long subjectId = courseSubjectModel.getSubjectId();
                long textbookId = courseSubjectModel.getTextbookId();

                // Lấy subjectName từ subjectNameMapByIds
                String subjectName = subjectNameMapByIds.getOrDefault(subjectId, "Unknown Subject");

                // Lấy lessonsSections từ textbookContentMapByIds
                List<AdminLessonSectionResponse> lessonsSections = textbookContentMapByIds.getOrDefault(textbookId, Collections.emptyList());
                return this.webModelToResponseConverter.toAllCourseTextbookSectionResponse(subjectId, subjectName, lessonsSections);
            })
            .collect(Collectors.toList());
        if (allCourseTextbookSections.isEmpty()) {
            return Collections.emptyList();
        }
        return allCourseTextbookSections;
    }

    public List<AllCourseTextbookExercise> getAllCourseWithTextbookExercise(String slug) {
        CourseModel courseModel = this.courseService.getCourseBySlug(slug);
        List<CourseSubjectModel> courseSubjectModels = this.courseSubjectService.getCourseSubjectsByCourseId(courseModel.getId());
        Set<Long> subjectIds = courseSubjectModels.stream().map(CourseSubjectModel::getSubjectId).filter(id -> id > 0).collect(Collectors.toSet());
        Set<Long> textbookIds = courseSubjectModels.stream().map(CourseSubjectModel::getTextbookId).filter(id -> id > 0).collect(Collectors.toSet());
        Map<Long, String> subjectNameMapByIds = this.subjectService.getSubjectNameMapByIds(subjectIds);
        Map<Long, List<AdminLessonExerciseResponse>> textbookContentMapByIds = textbookIds.stream().collect(Collectors.toMap(
            textbookId -> textbookId,
            textbookId -> this.getLessonsExercisesByTextbookId(textbookId).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList())
        ));
        List<AllCourseTextbookExercise> allCourseTextbookSections = courseSubjectModels.stream()
            .map(courseSubjectModel -> {
                long subjectId = courseSubjectModel.getSubjectId();
                long textbookId = courseSubjectModel.getTextbookId();

                // Lấy subjectName từ subjectNameMapByIds
                String subjectName = subjectNameMapByIds.getOrDefault(subjectId, "Unknown Subject");

                // Lấy lessonsSections từ textbookContentMapByIds
                List<AdminLessonExerciseResponse> lessonsExercises = textbookContentMapByIds.getOrDefault(textbookId, Collections.emptyList());
                return this.webModelToResponseConverter.toAllCourseTextbookExerciseResponse(subjectId, subjectName, lessonsExercises);
            })
            .collect(Collectors.toList());
        if (allCourseTextbookSections.isEmpty()) {
            return Collections.emptyList();
        }
        return allCourseTextbookSections;
    }

    public List<AdminLessonSectionResponse> getLessonsSectionsByTextbookId(long textbookId) {
        // Lấy ra danh sách id của các bài học theo priority tăng dần
        List<TextbookLessonModel> textbookLessonModels = this.textbookLessonService.getTextbookLessonsByTextbookId(textbookId);
        if (textbookLessonModels.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> lessonIds = newArrayList(textbookLessonModels, TextbookLessonModel::getLessonId);
        // Lấy ra thông tin của các bài học
        List<LessonModel> lessonModels = newArrayList(lessonIds, this.lessonService::getLessonById);
        // Lấy ra các đề mục của các bài học theo priority tăng dần
        Map<Long, List<SectionModel>> sectionsMapByLessonIds = lessonIds.stream()
            .collect(Collectors.toMap(lessonId ->
                    lessonId,
                this.sectionService::getListByLessonId
            ));
        return this.adminLessonsSectionsModelDecorator.decorateLessonsSectionsModel(textbookLessonModels, lessonModels, sectionsMapByLessonIds);
    }

    public List<AdminLessonExerciseResponse> getLessonsExercisesByTextbookId(long textbookId) {
        // Lấy ra danh sách id của các bài học theo priority tăng dần
        List<TextbookLessonModel> textbookLessonModels = this.textbookLessonService.getTextbookLessonsByTextbookId(textbookId);
        if (textbookLessonModels.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> lessonIds = newArrayList(textbookLessonModels, TextbookLessonModel::getLessonId);
        // Lấy ra thông tin của các bài học
        List<LessonModel> lessonModels = newArrayList(lessonIds, this.lessonService::getLessonById);
        // Lấy ra danh sách các bài tập của bài học theo priority tăng dần
        Map<Long, List<ExerciseModelWithPriority>> exercisesMapByLessonIds = lessonIds.stream()
            .collect(Collectors.toMap(
                lessonId -> lessonId,
                lessonId -> {
                    List<LessonExerciseModel> models = this.lessonExerciseService.getLessonExerciseByLessonId(lessonId);
                    List<Long> ids = models.stream()
                        .map(LessonExerciseModel::getExerciseId)
                        .filter(id -> id > 0)
                        .collect(Collectors.toList());
                    List<ExerciseModel> exerciseModels = this.exerciseService.getListByExerciseIds(ids);
                    return newArrayList(exerciseModels, exerciseModel -> this.webModelToModelConverter.toExerciseModelWithPriority(
                        exerciseModel,
                        models.stream()
                            .filter(model -> model.getExerciseId() == exerciseModel.getId())
                            .map(LessonExerciseModel::getPriority)
                            .findFirst()
                            .orElse(0.0f)
                    ));
                }
            ));
        return this.adminLessonsExercisesModelDecorator.decorateAdminLessonExerciseModel(textbookLessonModels, lessonModels, exercisesMapByLessonIds);
    }
}
