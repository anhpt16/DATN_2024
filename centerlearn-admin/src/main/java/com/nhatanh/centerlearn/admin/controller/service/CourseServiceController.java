package com.nhatanh.centerlearn.admin.controller.service;

import com.nhatanh.centerlearn.admin.controller.decorator.AdminCourseModelDecorator;
import com.nhatanh.centerlearn.admin.controller.decorator.AdminCourseSubjectModelDecorator;
import com.nhatanh.centerlearn.admin.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.admin.model.*;
import com.nhatanh.centerlearn.admin.response.AdminCourseResponse;
import com.nhatanh.centerlearn.admin.response.AdminCourseSubjectResponse;
import com.nhatanh.centerlearn.admin.service.CourseService;
import com.nhatanh.centerlearn.admin.service.CourseSubjectService;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceController {
    private final CourseService courseService;
    private final CourseSubjectService courseSubjectService;
    private final AdminCourseModelDecorator adminCourseModelDecorator;
    private final AdminCourseSubjectModelDecorator adminCourseSubjectModelDecorator;

    public void addCourse(AddCourseModel model) {
        this.courseService.addCourse(model);
    }

    public void updateCourse(UpdateCourseModel model) {
        this.courseService.updateCourse(model);
    }

    public AdminCourseResponse getCourseById(long id) {
        CourseModel courseModel = this.courseService.getCourseById(id);
        if (courseModel == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        return this.adminCourseModelDecorator.decorateAdminCourseModel(courseModel);
    }

    public PaginationModel<AdminCourseResponse> getCoursePagination(CourseFilterCriteria criteria, int page, int size) {
        PaginationModel<CourseModel> coursePaginationModel = this.courseService.getCoursePagination(criteria, page, size);
        return this.adminCourseModelDecorator.decorateAdminCoursePaginationModel(coursePaginationModel);
    }

    public List<CourseStatus> getCourseStatuses() {
        List<CourseStatus> courseStatuses = Arrays.asList(CourseStatus.values());
        return courseStatuses;
    }

    public List<CourseType> getCourseTypes() {
        List<CourseType> courseTypes = Arrays.asList(CourseType.values());
        return courseTypes;
    }

    public List<AdminCourseSubjectResponse> getCourseSubjectByCourseId(long courseId) {
        List<CourseSubjectModel> courseSubjectModels = this.courseSubjectService.getCourseSubjectsByCourseId(courseId);
        if (courseSubjectModels.isEmpty()) {
            return Collections.emptyList();
        }
        return this.adminCourseSubjectModelDecorator.decorateCourseSubjectModel(courseSubjectModels);
    }

    public void addCourseSubject(long courseId, long subjectId, long textbookId) {
        this.courseSubjectService.addCourseSubject(courseId, subjectId, textbookId);
    }

    public void deleteCourseSubject(long courseId, long subjectId) {
        this.courseSubjectService.deleteCourseSubject(courseId, subjectId);
    }

    public void updateManagerForCourse(long courseId, long managerId) {
        this.courseService.updateManager(courseId, managerId);
    }
}
