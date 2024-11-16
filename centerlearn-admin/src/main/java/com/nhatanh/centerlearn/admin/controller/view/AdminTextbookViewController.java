package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/admin/textbook")
public class AdminTextbookViewController {

    @DoGet("/list")
    public View initListTextbook(

    ) {

        return View.builder()
            .template("/contents/textbook/textbook-list")
            .build();
    }

    @DoGet("/list-lesson")
    public View initListLesson(

    ) {

        return View.builder()
            .template("/contents/textbook/textbook-list-lesson")
            .build();
    }

    @DoGet("/list-detail-lesson")
    public View initListDetailLesson(

    ) {

        return View.builder()
            .template("/contents/textbook/textbook-list-detail-lesson")
            .build();
    }
}
