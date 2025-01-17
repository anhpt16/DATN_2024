package com.nhatanh.centerlearn.web.controller.view;

import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.web.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.web.controller.service.OrderServiceController;
import com.nhatanh.centerlearn.web.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.AdminLessonSectionResponse;
import com.nhatanh.centerlearn.web.response.AllCourseTextbookSection;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.validator.CourseValidator;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller("/personal")
public class OwnerCourseController {
    private final OrderServiceController orderServiceController;
    private final CourseValidator courseValidator;
    private final CourseServiceController courseServiceController;
    private final TextbookServiceController textbookServiceController;

    @DoGet("/course")
    public View initOwnerCourse() {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        List<WebCourseDetailResponse> courseModels = this.orderServiceController.getOwnerCourse(accountId);
        return View.builder()
            .addVariable("courses", courseModels)
            .template("owner_course")

            .build();
    }

    @DoGet("/course/{slug}")
    public View initCourseDetail(
        @PathVariable String slug
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.courseValidator.validateDetail(slug, accountId);
        List<AllCourseTextbookSection> allCourseTextbookSections = this.textbookServiceController.getAllCourseWithTextbookSection(slug);
        CourseModel courseModel = this.courseServiceController.getCourseBySlug(slug);
        long courseId = courseModel.getId();
        return View.builder()
            .addVariable("courseId", courseId)
            .addVariable("subjects", allCourseTextbookSections)
            .template("owner_course_detail")
            .build();
    }
}
