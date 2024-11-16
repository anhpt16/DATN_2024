package com.nhatanh.centerlearn.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.view.View;

@Controller
public class WebLearnBlogController {
    @DoGet("/blog")
    public View blog(
        @PathVariable long id
    ) {
        return View.builder()
            .template("blog")
            .build();
    }
}
