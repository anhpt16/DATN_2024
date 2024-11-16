package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/admin/course")
public class AdminCourseViewController {

    @DoGet("/add")
    public View initAddCourse(

    ) {
        return View.builder()
            .template("/contents/course/course-add")
            .build();
    }

    @DoGet("/list")
    public View initListCourse(

    ) {
        return View.builder()
            .template("/contents/course/course-list")
            .build();
    }

    @DoGet("/textbook")
    public View initTextbookCourse(

    ) {
        return View.builder()
            .template("/contents/course/course-textbook")
            .build();
    }
}
