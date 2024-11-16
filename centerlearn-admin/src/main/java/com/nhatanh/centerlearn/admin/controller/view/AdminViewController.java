package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/dashboard")
public class AdminViewController {
    @DoGet("/")
    public View dashboard() {
        return View.builder()
            .template("dashboard")
            .build();
    }
}
