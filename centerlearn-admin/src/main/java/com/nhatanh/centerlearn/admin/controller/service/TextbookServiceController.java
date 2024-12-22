package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminLessonsExercisesModelDecorator;
import com.nhatanh.centerlearn.admin.controller.decorator.AdminLessonsSectionsModelDecorator;
import com.nhatanh.centerlearn.admin.controller.decorator.AdminTextbookModelDecorator;
import com.nhatanh.centerlearn.admin.converter.AdminModelToModelConverter;
import com.nhatanh.centerlearn.admin.converter.AdminModelToResponseConverter;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.AdminLessonExerciseResponse;
import com.nhatanh.centerlearn.admin.response.AdminLessonSectionResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookShortResponse;
import com.nhatanh.centerlearn.admin.service.SubjectTextbookService;
import com.nhatanh.centerlearn.admin.service.TextbookLessonService;
import com.nhatanh.centerlearn.admin.service.TextbookService;
import com.nhatanh.centerlearn.common.entity.SubjectTextbookId;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.*;
import com.nhatanh.centerlearn.common.service.ExerciseService;
import com.nhatanh.centerlearn.common.service.LessonExerciseService;
import com.nhatanh.centerlearn.common.service.LessonService;
import com.nhatanh.centerlearn.common.service.SectionService;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class TextbookServiceController {
    private final TextbookService textbookService;
    private final SubjectTextbookService subjectTextbookService;
    private final TextbookLessonService textbookLessonService;
    private final LessonService lessonService;
    private final SectionService sectionService;
    private final ExerciseService exerciseService;
    private final LessonExerciseService lessonExerciseService;
    private final AdminModelToResponseConverter adminModelToResponseConverter;
    private final AdminModelToModelConverter adminModelToModelConverter;
    private final AdminTextbookModelDecorator adminTextbookModelDecorator;
    private final AdminLessonsSectionsModelDecorator adminLessonsSectionsModelDecorator;
    private final AdminLessonsExercisesModelDecorator adminLessonsExercisesModelDecorator;

    public void addTextbook(AddTextbookModel addTextbookModel) {
        long textbookId = this.textbookService.addTextbook(addTextbookModel);
        if (textbookId == 0) {
            throw new FailedCreationException("Fail to create Textbook");
        }
        // Save TextbookSubject
        if (addTextbookModel.getSubjectId() > 0) {
            SubjectTextbookModel subjectTextbookModel = this.adminModelToModelConverter.toSubjectTextbookModelConverter(addTextbookModel.getSubjectId(), textbookId);
            SubjectTextbookId subjectTextbookId = this.subjectTextbookService.addSubjectTextbook(subjectTextbookModel);
            if (subjectTextbookId == null) {
                throw new FailedCreationException("Fail to create SubjectTextbook");
            }
        }
    }

    public PaginationModel<AdminTextbookResponse> getTextbookPaginationByFilter(TextbookFilterCriteria criteria, int page, int size) {
        PaginationModel<TextbookModel> textbookPaginationModel = this.textbookService.getTextbookPaginationByFilter(criteria, page, size);
        return this.adminTextbookModelDecorator.decorateTextbookPaginationModel(textbookPaginationModel);
    }

    public AdminTextbookResponse getTextbookById(long id) {
        TextbookModel textbookModel = this.textbookService.getTextBookById(id);
        if (textbookModel == null) {
            throw new HttpNotFoundException("Textbook with id: " + id + " invalid");
        }
        return this.adminModelToResponseConverter.toTextbookResponse(textbookModel);
    }

    public AdminTextbookResponse getTextbookBySlug(String slug) {
        TextbookModel textbookModel = this.textbookService.getTextbookBySlug(slug);
        if (textbookModel == null) {
            throw new HttpNotFoundException("Textbook with slug: " + slug + " invalid");
        }
        return this.adminModelToResponseConverter.toTextbookResponse(textbookModel);
    }

    public void updatedTextbookById(SaveTextbookModel model) {
        this.textbookService.updateTextbook(model);
    }

    public List<TextbookStatus> getAllTextbookStatus() {
        return Arrays.asList(TextbookStatus.values());
    }

    public List<AdminTextbookShortResponse> getAll() {
        List<TextbookModel> textbookModels = this.textbookService.getAll();
        if (textbookModels.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(textbookModels, this.adminModelToResponseConverter::toTextbookShortResponse);
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
                    return newArrayList(exerciseModels, exerciseModel -> this.adminModelToModelConverter.toExerciseModelWithPriority(
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
