package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/admin/textbook")
public class AdminTextbookViewController {

    @DoGet("/list")
    public View initTextBook(

    ) {

        return View.builder()
            .template("/contents/textbook/list-textbook")
            .build();
    }

    @DoGet("/manage")
    public View initManageTextbook(

    ) {

        return View.builder()
            .template("/contents/textbook/manage_textbook")
            .build();
    }

    @DoGet("/detail")
    public View initDetailTextbook(

    ) {

        return View.builder()
            .template("/contents/textbook/detail_textbook")
            .build();
    }
}
