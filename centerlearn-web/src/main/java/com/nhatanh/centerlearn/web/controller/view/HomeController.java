package com.nhatanh.centerlearn.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor

public class HomeController {
    @DoGet("/")
    public View home() {
        return View.builder()
            .template("home")
            .build();
    }
}
