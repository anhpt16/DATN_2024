package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.model.AccountLoginModel;
import com.nhatanh.centerlearn.admin.request.AuthAccountRequest;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.tvd12.ezyhttp.client.HttpClient;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.Api;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.RequestBody;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpStatus;

@Api
@Controller("/api/v1/admin/login")
@AllArgsConstructor
public class AdminLoginApiController {
    private final AccountServiceController accountServiceController;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoPost
    public ResponseEntity authLogin(
        @RequestBody AuthAccountRequest accountRequest
    ) {
        AccountLoginModel accountLoginModel = this.requestToModelConverter.toAccountLoginModel(accountRequest);
        if (!this.accountServiceController.getAccountByUsernameAndPassword(accountLoginModel)) {
            throw new ResourceNotFoundException("Wrong username or password");
        }
        String token = this.accountServiceController.getToken(accountLoginModel.getUsername());
        String location = "/account/user?lang=vi";
        return ResponseEntity.builder()
//            .header("Location", location)
            .header("token", token)
            .status(HttpStatus.FOUND_302)
            .build();
    }
}
