package com.nhatanh.centerlearn.common.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.AccountServiceController;
import com.nhatanh.centerlearn.common.response.AccountResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.common.validator.AccountValidator;
import com.tvd12.ezyhttp.core.exception.HttpNotFoundException;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Api
@Controller("/api/v1/account")
@AllArgsConstructor
public class AccountApiController {
    private final AccountValidator accountValidator;
    private final AccountServiceController accountServiceController;

    @Authenticated
    @DoPut("/avatar/{imageId}")
    public ResponseEntity updateAccountAvatar(
        @PathVariable long imageId
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        // Validate
        this.accountValidator.validateAccountAvatar(accountId, imageId);
        this.accountServiceController.updateAccountAvatar(accountId, imageId);
        return ResponseEntity.noContent();
    }

    @Authenticated
    @DoGet("/personal")
    public ResponseEntity getAccountInfo() {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        AccountResponse accountResponse = this.accountServiceController.getAccountById(accountId);
        if (accountResponse == null) {
            throw new HttpNotFoundException("Account Invalid");
        }
        return ResponseEntity.ok(accountResponse);
    }

    @Authenticated
    @DoPut("/displayname")
    public ResponseEntity updateAccountDisplayName(
        @RequestParam (value = "displayName") String displayName
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        //validate
        this.accountValidator.validateDisplayName(displayName);
        this.accountServiceController.updateAccountDisplayName(accountId, displayName);
        return ResponseEntity.noContent();
    }

    @Authenticated
    @DoPut("/email")
    public ResponseEntity updateAccountEmail(
        @RequestParam (value = "email") String email
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        //validate
        this.accountValidator.validateEmail(email);
        this.accountServiceController.updateAccountEmail(accountId, email);
        return ResponseEntity.noContent();
    }

    @Authenticated
    @DoPut("/phone")
    public ResponseEntity updateAccountPhone(
        @RequestParam (value = "phone") String phone
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        //validate
        this.accountValidator.validatePhone(phone);
        this.accountServiceController.updateAccountPhone(accountId, phone);
        return ResponseEntity.noContent();
    }
}
