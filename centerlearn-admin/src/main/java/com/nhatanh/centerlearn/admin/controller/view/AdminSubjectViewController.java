package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/admin/subject")
public class AdminSubjectViewController {

    @DoGet("/manage")
    public View initAddSubject(

    ) {

        return View.builder()
            .template("/contents/subject/manage_subject")
            .build();
    }

    @DoGet("/list")
    public View initListSubject(

    ) {

        return View.builder()
            .template("/contents/subject/list_subject")
            .build();
    }

}
