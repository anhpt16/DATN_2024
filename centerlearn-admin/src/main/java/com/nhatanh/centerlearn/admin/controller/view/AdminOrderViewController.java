package com.nhatanh.centerlearn.admin.controller.view;

import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller("/admin/order")
public class AdminOrderViewController {

    @DoGet
    public View initOrder() {

        return View.builder()
            .template("/contents/info/order")
            .build();
    }
}
