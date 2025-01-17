package com.nhatanh.centerlearn.web.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/login")
public class LoginViewController {


    @DoGet
    public View initLogin() {

        return View.builder()
            .template("login")
            .build();
    }
}
