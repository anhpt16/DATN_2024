package com.nhatanh.centerlearn.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;


@Controller
public class WebLearnCourseController {

    @DoGet("/courses")
    public View course() {
        return View.builder()
            .template("course")
            .build();
    }
}
