package com.nhatanh.centerlearn.web.controller.view;

import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.web.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.web.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/course")
public class WebLearnCourseController {
    private final CourseServiceController courseServiceController;

    @DoGet("/list")
    public View course(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        List<CourseType> courseTypes = this.courseServiceController.getCourseTypes();
        PaginationModel<WebCourseResponse> coursePaginationResponse = this.courseServiceController.getCoursePagination(
            CourseFilterCriteria.builder().build(),
            page,
            size
        );
        return View.builder()
            .addVariable("types", courseTypes)
            .addVariable("coursePagination", coursePaginationResponse)
            .template("course")
            .build();
    }

    @DoGet("/{slug}")
    public View initCourseDetail(
        @PathVariable String slug
    ) {
        WebCourseDetailResponse courseDetailResponse = this.courseServiceController.getCourseDetailBySlug(slug);
        return View.builder()
            .addVariable("course", courseDetailResponse)
            .template("course_detail")
            .build();
    }
}
