package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.admin.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.admin.filter.CourseFilterCriteria;
import com.nhatanh.centerlearn.admin.response.AdminCourseResponse;
import com.nhatanh.centerlearn.common.enums.CourseStatus;
import com.nhatanh.centerlearn.common.enums.CourseType;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/admin/course")
public class AdminCourseViewController {
    private final CourseServiceController courseServiceController;


    @DoGet
    public View initCourse(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        List<CourseStatus> courseStatuses = this.courseServiceController.getCourseStatuses();
        List<CourseType> courseTypes = this.courseServiceController.getCourseTypes();
        PaginationModel<AdminCourseResponse> coursePaginationResponse = this.courseServiceController.getCoursePagination(
            CourseFilterCriteria.builder().build(),
            page,
            size
        );
        return View.builder()
            .addVariable("coursePagination", coursePaginationResponse)
            .addVariable("statuses", courseStatuses)
            .addVariable("types", courseTypes)
            .template("/contents/course/manage_course")
            .build();
    }
}
