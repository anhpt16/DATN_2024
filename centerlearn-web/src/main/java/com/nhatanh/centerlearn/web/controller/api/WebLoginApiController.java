package com.nhatanh.centerlearn.web.controller.api;

import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.web.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.web.converter.WebRequestToModelConverter;
import com.nhatanh.centerlearn.web.model.AccountLoginModel;
import com.nhatanh.centerlearn.web.request.AuthAccountRequest;
import com.nhatanh.centerlearn.web.response.AccountAvatarResponse;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@AllArgsConstructor
@Api
@Controller("/api/v1/login")
public class WebLoginApiController {
    private final WebRequestToModelConverter webRequestToModelConverter;
    private final AccountServiceController accountServiceController;

    @DoPost
    public ResponseEntity authLogin(
        @RequestBody AuthAccountRequest accountRequest,
        HttpServletResponse response
    ) {
        AccountLoginModel accountLoginModel = this.webRequestToModelConverter.toAccountLoginModel(accountRequest);
        if (!this.accountServiceController.getAccountByUsernameAndPassword(accountLoginModel)) {
            throw new HttpUnauthorizedException("Tài khoản hoặc mật khẩu không đúng");
        }
        String token = this.accountServiceController.getToken(accountLoginModel.getUsername());
        Cookie cookie = new Cookie("authToken", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86403600);
        response.addCookie(cookie);

        String location = "/?lang=vi";
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
