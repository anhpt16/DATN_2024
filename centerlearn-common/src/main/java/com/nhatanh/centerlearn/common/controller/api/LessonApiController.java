package com.nhatanh.centerlearn.common.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.LessonServiceController;
import com.nhatanh.centerlearn.common.controller.controller.SectionServiceController;
import com.nhatanh.centerlearn.common.converter.RequestToModelConverter;
import com.nhatanh.centerlearn.common.entity.LessonExerciseId;
import com.nhatanh.centerlearn.common.request.*;
import com.nhatanh.centerlearn.common.response.LessonResponse;
import com.nhatanh.centerlearn.common.response.SectionResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.common.validator.ExerciseValidator;
import com.nhatanh.centerlearn.common.validator.LessonValidator;
import com.nhatanh.centerlearn.common.validator.SectionValidator;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Api
@Controller("/api/v1/lesson")
@AllArgsConstructor
public class LessonApiController {
    private final LessonValidator lessonValidator;
    private final SectionValidator sectionValidator;
    private final ExerciseValidator exerciseValidator;
    private final LessonServiceController lessonServiceController;
    private final SectionServiceController sectionServiceController;
    private final RequestToModelConverter requestToModelConverter;

    @DoPost
    public ResponseEntity addLesson(
        @RequestBody AddLessonRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.lessonValidator.validate(request, accountId);
        this.lessonServiceController.addLesson(this.requestToModelConverter.toAddLessonModel(request, accountId));
        return ResponseEntity.noContent();
    }

    // Sửa thông tin một bài học
    @DoPut("/{id}")
    public ResponseEntity updateLesson(
        @PathVariable long id,
        @RequestBody UpdateLessonRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.lessonValidator.validate(request, id, accountId);
        this.lessonValidator.validateNull(request);
        this.lessonServiceController.updateLesson(this.requestToModelConverter.toLessonModel(request, id));
        return ResponseEntity.noContent();
    }

    // Xem thông tin một bài học
    @DoGet("/{id}")
    public ResponseEntity getLessonById(
        @PathVariable long id
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.lessonValidator.validate(id, accountId);
        LessonResponse lessonResponse = this.lessonServiceController.getLessonById(id);
        if (lessonResponse == null) {
            return ResponseEntity.notFound("Lesson with id: " + id + " not found");
        }
        return ResponseEntity.ok(lessonResponse);
    }

    // Thêm một đề mục cho bài học
    @DoPost("/{lessonId}/section")
    public ResponseEntity addSectionForLesson(
        @PathVariable long lessonId,
        @RequestBody AddSectionRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.sectionValidator.validate(request, accountId, lessonId);
        this.lessonServiceController.addSection(this.requestToModelConverter.toAddSectionModel(request, accountId, lessonId));
        return ResponseEntity.noContent();
    }

    // Thêm một bài tập đã có cho bài học
    @DoPost("/{lessonId}/exercise/{exerciseId}")
    public ResponseEntity addExerciseForLessonWithLessonIdAndExerciseId(
        @PathVariable long lessonId,
        @PathVariable long exerciseId,
        @RequestParam float priority
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validate(lessonId, exerciseId, accountId);
        this.lessonServiceController.addExerciseExistForLesson(this.requestToModelConverter.toLessonExerciseModel(lessonId, exerciseId, priority));
        return ResponseEntity.noContent();
    }

    // Thêm một bài tập mới cho bài học
    @DoPost("/{lessonId}/exercise")
    public ResponseEntity addNewExerciseForLesson(
        @PathVariable long lessonId,
        @RequestBody AddExerciseRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validate(request, accountId, lessonId);
        this.lessonServiceController.addExerciseForLesson(this.requestToModelConverter.toAddExerciseModel(request, accountId), lessonId,request.getPriority());
        return ResponseEntity.noContent();
    }

    // Xóa một bài tập ra khỏi một bài học
    @DoDelete("/{lessonId}/exercise/{exerciseId}")
    public ResponseEntity deleteExerciseFromLesson(
        @PathVariable long lessonId,
        @PathVariable long exerciseId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validateDelete(lessonId, exerciseId, accountId);
        this.lessonServiceController.deleteLessonExerciseById(new LessonExerciseId(lessonId, exerciseId));
        return ResponseEntity.noContent();
    }

    // Xem một đề mục của một bài học
    @DoGet("/{lessonId}/section/{sectionId}")
    public ResponseEntity getSectionById(
        @PathVariable long lessonId,
        @PathVariable long sectionId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.sectionValidator.validate(lessonId, sectionId, accountId);
        SectionResponse sectionResponse = this.sectionServiceController.getSectionById(sectionId);
        return ResponseEntity.ok(sectionResponse);
    }

    // Sửa một đề mục của bài học
    @DoPut("/{lessonId}/section/{sectionId}")
    public ResponseEntity updateSectionById(
        @PathVariable long lessonId,
        @PathVariable long sectionId,
        @RequestBody SaveSectionRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.sectionValidator.validateSave(sectionId, lessonId, accountId, request);
        this.sectionValidator.validateNull(request);
        this.sectionServiceController.updateSection(this.requestToModelConverter.toSaveSectionModel(request, sectionId));
        return ResponseEntity.noContent();
    }

    // Xóa một đề mục của một bài học
    @DoDelete("/{lessonId}/section/{sectionId}")
    public ResponseEntity deleteSectionById(
        @PathVariable long lessonId,
        @PathVariable long sectionId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.sectionValidator.validateDelete(lessonId, sectionId, accountId);
        this.sectionServiceController.deleteSection(sectionId);
        return ResponseEntity.noContent();
    }


}
