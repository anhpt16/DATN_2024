package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.LessonServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddTextbookRequest;
import com.nhatanh.centerlearn.admin.request.SaveTextbookRequest;
import com.nhatanh.centerlearn.admin.response.AdminLessonExerciseResponse;
import com.nhatanh.centerlearn.admin.response.AdminLessonSectionResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookShortResponse;
import com.nhatanh.centerlearn.admin.validator.TextbookValidator;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.request.AddLessonRequest;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Api
@Controller("/api/v1/textbook")
@AllArgsConstructor
/*
    Lấy danh sách giáo trình theo điều kiện lọc
    Tạo một giáo trình
    Sửa một giáo trình theo id
    Xem chi tiết một giáo trình theo id
    Thêm bài học cho một giáo trình -> Thêm đề mục cho bài học -> Thêm bài tập cho bài học
 */
public class AdminTextbookApiController {
    private final TextbookServiceController textbookServiceController;
    private final LessonServiceController lessonServiceController;
    private final TextbookValidator textbookValidator;
    private final AdminRequestToModelConverter adminRequestToModelConverter;

    @DoGet
    public PaginationModel<AdminTextbookResponse> getTextbookPaginationByFilter(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size,
        @RequestParam (value = "id") long id,
        @RequestParam (value = "name") String name,
        @RequestParam (value = "author") String author,
        @RequestParam (value = "status") String status,
        @RequestParam (value = "sortOrder", defaultValue = "2") int sortOrder,
        @RequestParam (value = "subjectId") long subjectId
    ) {
        TextbookFilterCriteria criteria = TextbookFilterCriteria.builder()
            .id(id)
            .name(name)
            .author(author)
            .status(status)
            .sortOrder(sortOrder)
            .subjectId(subjectId)
            .build();
        this.textbookValidator.validate(criteria);
        PaginationModel<AdminTextbookResponse> textbookPaginationResponse = this.textbookServiceController.getTextbookPaginationByFilter(criteria, page, size);
        return textbookPaginationResponse;
    }

    @DoGet("/{id}")
    public ResponseEntity getTextbookById(
        @PathVariable long id
    ) {
        AdminTextbookResponse adminTextbookResponse = this.textbookServiceController.getTextbookById(id);
        if (adminTextbookResponse == null) {
            return ResponseEntity.notFound("Textbook with id: " + id + " not found");
        }
        return ResponseEntity.ok(adminTextbookResponse);
    }

    @DoPost
    public ResponseEntity addTextbook(
        @RequestBody AddTextbookRequest request
    ) {
        // validate
        this.textbookValidator.validate(request);
        this.textbookServiceController.addTextbook(this.adminRequestToModelConverter.toAddTextbookModel(request));
        return ResponseEntity.noContent();
    }

    @DoPut("/{id}")
    public ResponseEntity updateTextbookById(
        @PathVariable long id,
        @RequestBody SaveTextbookRequest request
        ) {
        // validate
        this.textbookValidator.validate(request, id);
        this.textbookValidator.validateNull(request);
        this.textbookServiceController.updatedTextbookById(this.adminRequestToModelConverter.toSaveTextbookModel(request, id));
        return ResponseEntity.noContent();
    }

    @DoGet("/statuses")
    public List<Map<String, String>> getTextbookStatuses() {
        return Arrays.stream(TextbookStatus.values())
            .map(TextbookStatus::toJson)
            .collect(Collectors.toList());
    }

    @DoGet("/all-short")
    public ResponseEntity getAllTextbookShort() {
        List<AdminTextbookShortResponse> textbookShortResponses = this.textbookServiceController.getAll();
        return ResponseEntity.ok(textbookShortResponses);
    }

    /* Common (Quản trị + Quản lý + Giảng viên)
        Thêm bài học -> Thêm đề mục cho bài học -> Thêm bài tập cho bài học
     */

    // Thêm một bài học đã có cho giáo trình
    @DoPost("/{textbookId}/lesson/{lessonId}")
    public ResponseEntity addLessonToTextbookWithLessonId(
        @PathVariable long textbookId,
        @PathVariable long lessonId,
        @RequestParam float priority
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validate(textbookId, lessonId, accountId);
        this.lessonServiceController.addExistLessonForTextbook(lessonId, textbookId, priority);
        return ResponseEntity.noContent();
    }

    // Thêm một bài học mới cho giáo trình
    @DoPost("/{textbookId}/lesson")
    public ResponseEntity addNewLessonToTextbook(
        @PathVariable long textbookId,
        @RequestBody AddLessonRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validate(request, accountId, textbookId);
        this.lessonServiceController.addNewLessonForTextbook(this.adminRequestToModelConverter.toAddLessonModel(request, accountId), textbookId);
        return ResponseEntity.noContent();
    }

    // Xóa một bài học khỏi giáo trình
    @DoDelete("/{textbookId}/lesson/{lessonId}")
    public ResponseEntity deleteLessonFromTextbook(
        @PathVariable long textbookId,
        @PathVariable long lessonId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validateDelete(textbookId, lessonId, accountId);
        this.lessonServiceController.deleteLessonForTextbook(lessonId, textbookId);
        return ResponseEntity.noContent();
    }

    // Sửa một bài học trong giáo trình
    @DoPut("/{textbookId}/lesson/{lessonId}")
    public ResponseEntity updateLessonFromTextbook(
        @PathVariable long textbookId,
        @PathVariable long lessonId,
        @RequestParam Float priority
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validatePut(textbookId, lessonId, accountId);
        this.textbookValidator.validate(priority);
        this.lessonServiceController.updatePriorityForTextbookLesson(lessonId, textbookId, priority);
        return ResponseEntity.noContent();
    }

    // Lấy danh sách các bài học kèm các đề mục của các bài học trong một giáo trình
    @DoGet("/{textbookId}/lessons/sections")
    public List<AdminLessonSectionResponse> getLessonsSectionsByTextbookId(
        @PathVariable long textbookId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validate(textbookId);
        List<AdminLessonSectionResponse> lessonSectionResponses = this.textbookServiceController.getLessonsSectionsByTextbookId(textbookId);
        if (lessonSectionResponses.isEmpty()) {
            return Collections.emptyList();
        }
        return lessonSectionResponses;
    }

    // Lấy danh sách các bài học kèm các bài tập các các bài học trong một giáo trình
    @DoGet("/{textbookId}/lessons/exercises")
    public List<AdminLessonExerciseResponse> getLessonsExercisesByTextbookId(
        @PathVariable long textbookId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.textbookValidator.validate(textbookId);
        List<AdminLessonExerciseResponse> lessonExerciseResponses = this.textbookServiceController.getLessonsExercisesByTextbookId(textbookId);
        if (lessonExerciseResponses.isEmpty()) {
            return Collections.emptyList();
        }
        return lessonExerciseResponses;
    }
}
