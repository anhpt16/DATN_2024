package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.model.AccountLoginModel;
import com.nhatanh.centerlearn.admin.request.AuthAccountRequest;
import com.nhatanh.centerlearn.admin.response.AccountAvatarResponse;
import com.nhatanh.centerlearn.common.exception.ResourceNotFoundException;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.client.HttpClient;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpHeaderValue;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Api
@Controller("/api/v1/admin/login")
@AllArgsConstructor
public class AdminLoginApiController {
    private final AccountServiceController accountServiceController;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoPost
    public ResponseEntity authLogin(
        @RequestBody AuthAccountRequest accountRequest,
        HttpServletResponse response
    ) {
        AccountLoginModel accountLoginModel = this.requestToModelConverter.toAccountLoginModel(accountRequest);
        if (!this.accountServiceController.getAccountByUsernameAndPassword(accountLoginModel)) {
            throw new HttpUnauthorizedException("Tài khoản hoặc mật khẩu không đúng");
        }
        String token = this.accountServiceController.getToken(accountLoginModel.getUsername());
        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86403600);
        response.addCookie(cookie);

        String location = "/account/user?lang=vi";
        return ResponseEntity.builder()
            .header("location", location)
            .status(HttpStatus.OK_200)
            .build();
    }

    @DoGet("/user")
    public ResponseEntity getUser() {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid (token)"));
        System.out.println(accountId);
        AccountAvatarResponse accountAvatarResponse = this.accountServiceController.getAccountAvatarById(accountId);
        if (accountAvatarResponse == null) {
            throw new HttpUnauthorizedException("User Invalid (accountId)");
        }
        return ResponseEntity.ok(accountAvatarResponse);
    }
}
