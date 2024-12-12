package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.common.controller.controller.AccountServiceController;
import com.nhatanh.centerlearn.common.response.AccountResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@Controller("/admin")
public class AdminPersonalViewController {
    private final AccountServiceController accountServiceController;


    @DoGet("/personal")
    public View initPersonal() {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        AccountResponse accountResponse = this.accountServiceController.getAccountById(accountId);
        return View.builder()
            .addVariable("accountResponse", accountResponse)
            .template("/contents/info/user_info")
            .build();
    }
}
