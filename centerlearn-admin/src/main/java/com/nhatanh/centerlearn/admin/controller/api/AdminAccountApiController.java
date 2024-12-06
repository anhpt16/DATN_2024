package com.nhatanh.centerlearn.admin.controller.api;


import com.nhatanh.centerlearn.admin.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.request.SaveAccountResquest;
import com.nhatanh.centerlearn.admin.response.AdminAccountDetailResponse;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.response.AdminRoleResponse;
import com.nhatanh.centerlearn.admin.validator.AccountValidator;
import com.nhatanh.centerlearn.common.constant.Constants;
import com.nhatanh.centerlearn.common.enums.AccountStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Api
@Controller("/api/v1/accounts")
@AllArgsConstructor
public class AdminAccountApiController {
    private final AccountValidator accountValidator;
    private final AccountServiceController accountServiceController;
    private final AdminRequestToModelConverter requestToModelConverter;

    @Authenticated
    @DoPost("/add")
    public ResponseEntity addAccount(
        @RequestBody SaveAccountResquest resquest
        ) {
        System.out.println(resquest.toString());
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        resquest.setCreatorId(accountId);
        this.accountValidator.validate(resquest);
        this.accountServiceController.addAccount(this.requestToModelConverter.toSaveAccountModelConverter(resquest));
        return ResponseEntity.noContent();
    }

    @DoPost("/add-student")
    public ResponseEntity addStudentAccount(
        @RequestBody SaveAccountResquest resquest
    ) {
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElse(0L);
        resquest.setCreatorId(accountId);
        this.accountValidator.validate(resquest);
        resquest.setRoleId(Constants.ROLE_ID_STUDENT);
        this.accountServiceController.addAccount(this.requestToModelConverter.toSaveAccountModelConverter(resquest));
        return ResponseEntity.noContent();
    }

    @Authenticated
    @DoPost("/add-teacher")
    public ResponseEntity addTeacherAccount(
        @RequestBody SaveAccountResquest resquest
    ) {
        System.out.println(RequestContext.get("accountId"));
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        resquest.setCreatorId(accountId);
        this.accountValidator.validate(resquest);
        resquest.setRoleId(Constants.ROLE_ID_TEACHER);
        this.accountServiceController.addAccount(this.requestToModelConverter.toSaveAccountModelConverter(resquest));
        return ResponseEntity.noContent();
    }

    @DoGet("/")
    public PaginationModel<AdminAccountResponse> getAccountsByType(
        @RequestParam (value = "id") long id,
        @RequestParam (value = "username") String username,
        @RequestParam (value = "displayName") String displayName,
        @RequestParam (value = "email") String email,
        @RequestParam (value = "phone") String phone,
        @RequestParam (value = "status") int status,
        @RequestParam (value = "roleId") long roleId,
        @RequestParam (value = "startDate") LocalDate startDate,
        @RequestParam (value = "endDate") LocalDate endDate,
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        AccountFilterCriteria criteria = AccountFilterCriteria.builder()
            .id(id)
            .username(username)
            .displayName(displayName)
            .email(email)
            .phone(phone)
            .status(status)
            .roleId(roleId)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        System.out.println(criteria.toString());
        this.accountValidator.validateCriteriaFilter(criteria);
        PaginationModel<AdminAccountResponse> accountResponsePagination = this.accountServiceController.getAccountsByType(
            criteria,
            page,
            size
        );
        return accountResponsePagination;
    }

    @DoGet("/students")
    public PaginationModel<AdminAccountResponse> getStudentAccounts(
        @RequestParam (value = "id") long id,
        @RequestParam (value = "username") String username,
        @RequestParam (value = "displayName") String displayName,
        @RequestParam (value = "email") String email,
        @RequestParam (value = "phone") String phone,
        @RequestParam (value = "status") int status,
        @RequestParam (value = "startDate") LocalDate startDate,
        @RequestParam (value = "endDate") LocalDate endDate,
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        AccountFilterCriteria criteria = AccountFilterCriteria.builder()
            .id(id)
            .username(username)
            .displayName(displayName)
            .email(email)
            .phone(phone)
            .status(status)
            .roleId(Constants.ROLE_ID_STUDENT)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        System.out.println(criteria.toString());
        this.accountValidator.validateCriteriaFilter(criteria);
        PaginationModel<AdminAccountResponse> accountResponsePagination = this.accountServiceController.getAccountsByType(
            criteria,
            page,
            size
        );
        return accountResponsePagination;
    }

    @DoGet("/teachers")
    public PaginationModel<AdminAccountResponse> getTeacherAccounts(
        @RequestParam (value = "id") long id,
        @RequestParam (value = "username") String username,
        @RequestParam (value = "displayName") String displayName,
        @RequestParam (value = "email") String email,
        @RequestParam (value = "phone") String phone,
        @RequestParam (value = "status") int status,
        @RequestParam (value = "startDate") LocalDate startDate,
        @RequestParam (value = "endDate") LocalDate endDate,
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        AccountFilterCriteria criteria = AccountFilterCriteria.builder()
            .id(id)
            .username(username)
            .displayName(displayName)
            .email(email)
            .phone(phone)
            .status(status)
            .roleId(Constants.ROLE_ID_TEACHER)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        System.out.println(criteria.toString());
        this.accountValidator.validateCriteriaFilter(criteria);
        PaginationModel<AdminAccountResponse> accountResponsePagination = this.accountServiceController.getAccountsByType(
            criteria,
            page,
            size
        );
        return accountResponsePagination;
    }

    @DoGet("/{id}")
    public ResponseEntity getAccountDetailById(
        @PathVariable long id
    ) {
        AdminAccountDetailResponse accountDetailResponse = this.accountServiceController.getAccountDetailById(id);
        if (accountDetailResponse == null) {
            return ResponseEntity.notFound("Account with Id: " + id + " not found");
        }
        return ResponseEntity.ok(accountDetailResponse);
    }

    @DoGet("/email/{email}")
    public ResponseEntity getAccountByEmail(
        @PathVariable String email
    ) {
        AdminAccountResponse adminAccountResponse = this.accountServiceController.getAccountByEmail(email);
        if (adminAccountResponse == null) {
            return ResponseEntity.notFound("Account with email: " + email + " not found");
        }
        return ResponseEntity.ok(adminAccountResponse);
    }

    @DoGet("/id/{id}")
    public ResponseEntity getAccountById(
        @PathVariable long id
    ) {
        AdminAccountResponse adminAccountResponse = this.accountServiceController.getAccountById(id);
        if (adminAccountResponse == null) {
            return ResponseEntity.notFound("Account with id: " + id + " not found");
        }
        return ResponseEntity.ok(adminAccountResponse);
    }

    @DoGet("/{id}/roles/")
    public List<AdminRoleResponse> getAccountRolesByAccountId(
        @PathVariable long id
    ) {
        List<AdminRoleResponse> roleResponses = this.accountServiceController.getAccountRolesById(id);
        if (roleResponses.isEmpty()) {
            return Collections.emptyList();
        }
        return roleResponses;
    }

    @DoGet("/{id}/roles/not-assigned")
    public List<AdminRoleResponse> getNotAssignedRolesByAccountId(
        @PathVariable long id
    ) {
        this.accountValidator.validateGetNotAssignedRoles(id);
        List<AdminRoleResponse> roleResponses = this.accountServiceController.getNotAssignedRolesByAccountId(id);
        return roleResponses;
    }

    @DoPost("/{accountId}/roles/{roleId}")
    public ResponseEntity addAccountRole(
        @PathVariable long accountId,
        @PathVariable long roleId
    ) {
        this.accountValidator.validateAddAccountRole(accountId, roleId);
        this.accountServiceController.addAccountRole(this.requestToModelConverter.toAccountRoleModel(accountId, roleId));
        return ResponseEntity.noContent();
    }

    @DoDelete("/{accountId}/roles/{roleId}")
    public ResponseEntity deleteAccountRole(
        @PathVariable long accountId,
        @PathVariable long roleId
    ) {
        this.accountValidator.validateDeleteAccountRole(accountId, roleId);
        this.accountServiceController.deleteAccountRole(this.requestToModelConverter.toAccountRoleModel(accountId, roleId));
        return ResponseEntity.noContent();
    }

    @DoGet("/phone/{phone}")
    public ResponseEntity getAccountByPhone(
        @PathVariable String phone
    ) {
        AdminAccountResponse adminAccountResponse = this.accountServiceController.getAccountByPhone(phone);
        if (adminAccountResponse == null) {
            return ResponseEntity.notFound("Account with phone: " + phone + " not found");
        }
        return ResponseEntity.ok(adminAccountResponse);
    }

    @DoGet("/statuses")
    public List<Map<String, Object>> getAccountStatuses() {
        return Arrays.stream(AccountStatus.values())
            .map(AccountStatus::toJson)
            .collect(Collectors.toList());
    }

    @DoPut("/{id}/status/{name}")
    public ResponseEntity updateAccountStatus(
        @PathVariable long id,
        @PathVariable String name
    ) {
        this.accountValidator.validateUpdateAccountStatus(id, name);
        this.accountServiceController.updateAccountStatus(id, name);
        return ResponseEntity.noContent();
    }
}
