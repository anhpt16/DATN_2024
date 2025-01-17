package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddCourseRequest;
import com.nhatanh.centerlearn.admin.request.UpdateCourseRequest;
import com.nhatanh.centerlearn.admin.response.AdminCourseResponse;
import com.nhatanh.centerlearn.admin.response.AdminCourseSubjectResponse;
import com.nhatanh.centerlearn.admin.validator.CourseValidator;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Api
@Controller("/api/v1/admin/course")
@AllArgsConstructor
public class AdminCourseApiController {
    private final CourseValidator courseValidator;
    private final CourseServiceController courseServiceController;
    private final AdminRequestToModelConverter adminRequestToModelConverter;
    // Tạo mới khóa học
    @DoPost
    public ResponseEntity addCourse(
        @RequestBody AddCourseRequest request
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validate(request);
        this.courseServiceController.addCourse(this.adminRequestToModelConverter.toAddCourseModel(request, accountId));
        return ResponseEntity.noContent();
    }

    // Sửa khóa học
    @DoPut("/{id}")
    public ResponseEntity updateCourse(
        @PathVariable long id,
        @RequestBody UpdateCourseRequest request
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validate(request, id);
        this.courseValidator.validateNull(request);
        this.courseServiceController.updateCourse(this.adminRequestToModelConverter.toUpdateCourseModel(request, id));
        return ResponseEntity.noContent();
    }

    // Xem chi tiết khóa học
    @DoGet("/{id}")
    public ResponseEntity getCourseById(
        @PathVariable long id
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        AdminCourseResponse adminCourseResponse = this.courseServiceController.getCourseById(id);
        if (adminCourseResponse == null) {
            return ResponseEntity.notFound("Course Not Found");
        }
        return ResponseEntity.ok(adminCourseResponse);
    }

    // Xem danh sách khóa học
    @DoGet
    public PaginationModel<AdminCourseResponse> getCourses(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size,
        @RequestParam (value = "keyword") String keyword,
        @RequestParam (value = "sortOrder", defaultValue = "2") int sortOrder,
        @RequestParam (value = "courseType") String courseType,
        @RequestParam (value = "status") String status
    ) {
        CourseFilterCriteria criteria = CourseFilterCriteria.builder()
            .keyword(keyword)
            .sortOrder(sortOrder)
            .courseType(courseType)
            .status(status)
            .build();
        PaginationModel<AdminCourseResponse> coursePaginationResponse = this.courseServiceController.getCoursePagination(criteria, page, size);
        return coursePaginationResponse;
    }

    // Lấy ra các trạng thái của khóa học
    @DoGet("/statuses")
    public List<CourseStatus> getStatuses(
    ) {
        List<CourseStatus> courseStatuses = this.courseServiceController.getCourseStatuses();
        if (courseStatuses.isEmpty()) {
            return Collections.emptyList();
        }
        return courseStatuses;
    }

    @DoGet("/types")
    public List<CourseType> getTypes() {
        List<CourseType> courseTypes = this.courseServiceController.getCourseTypes();
        if (courseTypes.isEmpty()) {
            return Collections.emptyList();
        }
        return courseTypes;
    }

    // Lấy ra danh sách các môn học và giáo trình môn học của khóa học
    @DoGet("/{courseId}/subjects/textbooks")
    public List<AdminCourseSubjectResponse> getCourseSubjectByCourseId(
        @PathVariable long courseId
    ) {
        List<AdminCourseSubjectResponse> courseSubjectResponses = this.courseServiceController.getCourseSubjectByCourseId(courseId);
        if (courseSubjectResponses.isEmpty()) {
            return Collections.emptyList();
        }
        return courseSubjectResponses;
    }


    // Xóa môn học của một khóa học
    @DoDelete("/{courseId}/subject/{subjectId}")
    public ResponseEntity deleteCourseSubject(
        @PathVariable long courseId,
        @PathVariable long subjectId
    ) {
        this.courseValidator.validate(courseId, subjectId);
        this.courseServiceController.deleteCourseSubject(courseId, subjectId);
        return ResponseEntity.noContent();
    }

    // Cập nhật giáo trình cho một môn học của khóa học
    @DoPost("/{courseId}/subject/{subjectId}/textbook/{textbookId}")
    public ResponseEntity updateTextbookForCourseSubject(
        @PathVariable long courseId,
        @PathVariable long subjectId,
        @PathVariable long textbookId
    ) {
        this.courseValidator.validate(courseId, subjectId, textbookId);
        this.courseServiceController.addCourseSubject(courseId, subjectId, textbookId);
        return ResponseEntity.noContent();
    }

    // Cập nhật quản lý cho khóa học
    @DoPut("/{courseId}/manager/{managerId}")
    public ResponseEntity updateManagerForCourse(
        @PathVariable long courseId,
        @PathVariable long managerId
    ) {
        this.courseValidator.validateManager(courseId, managerId);
        this.courseServiceController.updateManagerForCourse(courseId, managerId);
        return ResponseEntity.noContent();
    }

}
