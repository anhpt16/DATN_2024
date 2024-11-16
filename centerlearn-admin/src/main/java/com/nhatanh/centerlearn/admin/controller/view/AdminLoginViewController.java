package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import lombok.AllArgsConstructor;
import com.tvd12.ezyhttp.server.core.view.View;


@AllArgsConstructor
@Controller("/admin/login")
public class AdminLoginViewController {

    @DoGet
    public View initLogin() {
        return View.builder()
            .template("/login")
            .build();
    }
}
