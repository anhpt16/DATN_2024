package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/error")
public class PageError {

    @DoGet("/forbiden")
    public View initForbidenPage() {
        return View.builder()
            .template("/errors/forbiden")
            .build();
    }
}
