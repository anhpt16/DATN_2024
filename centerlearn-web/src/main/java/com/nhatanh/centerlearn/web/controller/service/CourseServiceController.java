package com.nhatanh.centerlearn.web.controller.service;

import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.web.controller.decorator.WebCourseModelDecorator;
import com.nhatanh.centerlearn.web.converter.WebModelToResponseConverter;
import com.nhatanh.centerlearn.web.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.nhatanh.centerlearn.web.service.CourseService;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CourseServiceController {
    private final CourseService courseService;
    private final WebCourseModelDecorator webCourseModelDecorator;
    private final WebModelToResponseConverter webModelToResponseConverter;

    public PaginationModel<WebCourseResponse> getCoursePagination(CourseFilterCriteria criteria, int page, int size) {
        PaginationModel<CourseModel> coursePaginationModel = this.courseService.getCoursePagination(criteria, page, size);
        return this.webCourseModelDecorator.decorateAdminCoursePaginationModel(coursePaginationModel);
    }

    public List<CourseType> getCourseTypes() {
        List<CourseType> courseTypes = Arrays.asList(CourseType.values());
        return courseTypes;
    }

    public WebCourseDetailResponse getCourseDetailBySlug(String slug) {
        CourseModel courseModel = this.courseService.getCourseBySlug(slug);
        if (courseModel == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        return this.webCourseModelDecorator.decorateCourseModel(courseModel);
    }

    public CourseModel getCourseBySlug(String slug) {
        CourseModel courseModel = this.courseService.getCourseBySlug(slug);
        if (courseModel == null) {
            return null;
        }
        return courseModel;
    }

    public CourseModel getCourseById(long id) {
        CourseModel courseModel = this.courseService.getCourseById(id);
        if (courseModel == null) {
            return null;
        }
        return courseModel;
    }

    public WebCourseDetailResponse getCourseByCode(String code) {
        CourseModel courseModel = this.courseService.getCourseByCode(code);
        if (courseModel == null) {
            throw new HttpNotFoundException("Course Not Found");
        }
        return this.webCourseModelDecorator.decorateCourseModel(courseModel);
    }
}
