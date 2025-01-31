package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.SubjectServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddSubjectRequest;
import com.nhatanh.centerlearn.admin.request.SaveSubjectRequest;
import com.nhatanh.centerlearn.admin.response.AdminSubjectResponse;
import com.nhatanh.centerlearn.admin.response.AdminSubjectShortResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookShortResponse;
import com.nhatanh.centerlearn.admin.validator.SubjectValidator;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Api
@Controller("/api/v1/subject")
@AllArgsConstructor
public class AdminSubjectApiController {
    private final SubjectValidator subjectValidator;
    private final SubjectServiceController subjectServiceController;
    private final AdminRequestToModelConverter adminRequestToModelConverter;

    @DoGet("/all-short")
    public List<AdminSubjectShortResponse> getAllSubjectShorts() {
        List<AdminSubjectShortResponse> subjectShortResponses = this.subjectServiceController.getAllSubjectShort();
        if (subjectShortResponses.isEmpty()) {
            return Collections.emptyList();
        }
        return subjectShortResponses;
    }

    @DoPost
    public ResponseEntity addSubject(
        @RequestBody AddSubjectRequest request
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        //validate
        this.subjectValidator.validate(request, accountId);
        this.subjectServiceController.addSubject(this.adminRequestToModelConverter.toAddSubjectModel(request));
        return ResponseEntity.ok();
    }

    @DoPut("/{id}")
    public ResponseEntity updatedSubject(
        @PathVariable long id,
        @RequestBody SaveSubjectRequest request
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.subjectValidator.validate(request, accountId, id);
        this.subjectValidator.validateNull(request);
        this.subjectServiceController.updateSubject(this.adminRequestToModelConverter.toSaveSubjectModel(request, id));
        return ResponseEntity.noContent();
    }

    @DoGet
    public PaginationModel<AdminSubjectResponse> getSubjectPaginationByFilter(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size,
        @RequestParam (value = "name") String name,
        @RequestParam (value = "displayName") String displayName,
        @RequestParam (value = "status") String status,
        @RequestParam (value = "sortOrder") int sortOrder
    ) {
        SubjectFilterCriteria criteria = SubjectFilterCriteria.builder()
            .name(name)
            .displayName(displayName)
            .sortOrder(sortOrder)
            .status(status)
            .build();
        this.subjectValidator.validate(criteria);
        PaginationModel<AdminSubjectResponse> subjectPagination = this.subjectServiceController.getSubjectPaginationByFilter(criteria, page, size);
        return subjectPagination;
    }

    @DoGet("/{id}")
    public ResponseEntity getSubjectById(
        @PathVariable long id
    ) {
        AdminSubjectResponse adminSubjectResponse = this.subjectServiceController.getSubjectById(id);
        if (adminSubjectResponse == null) {
            return ResponseEntity.notFound("Subject with id: " + id + " not found");
        }
        return ResponseEntity.ok(adminSubjectResponse);
    }

    @DoGet("/statuses")
    public List<Map<String, String>> getSubjectStatues() {
        return Arrays.stream(SubjectStatus.values())
            .map(SubjectStatus::toJson)
            .collect(Collectors.toList());
    }

    // Thêm giáo trình cho một môn học
    @DoPost("/{subjectId}/textbook")
    public ResponseEntity addSubjectTextbook(
        @PathVariable long subjectId,
        @RequestParam (value = "textbookId") long textbookId
    ) {
        // Validate
        this.subjectValidator.validateAdd(subjectId, textbookId);
        this.subjectServiceController.addSubjectTextbookById(subjectId, textbookId);
        return ResponseEntity.noContent();
    }

    // Xóa giáo trình của một môn học
    @DoDelete("/{subjectId}/textbook")
    public ResponseEntity deleteSubjectTextbook(
        @PathVariable long subjectId,
        @RequestParam (value = "textbookId") long textbookId
    ) {
        //validate
        this.subjectValidator.validateDelete(subjectId, textbookId);
        this.subjectServiceController.deleteSubjectTextbookById(subjectId, textbookId);
        return ResponseEntity.noContent();
    }

    // Lấy ra các giáo trình của một môn học
    @DoGet("/{subjectId}/textbook")
    public ResponseEntity getSubjectTextbooks(
        @PathVariable long subjectId
    ) {
        this.subjectValidator.validateGet(subjectId);
        List<AdminTextbookShortResponse> textbookShortResponses = this.subjectServiceController.getTextbooksBySubjectId(subjectId);
        return ResponseEntity.ok(textbookShortResponses);
    }

}
