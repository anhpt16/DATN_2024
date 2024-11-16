package com.nhatanh.centerlearn.admin.controller.api;


import com.nhatanh.centerlearn.admin.controller.service.AccountServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.AccountFilterCriteria;
import com.nhatanh.centerlearn.admin.request.SaveAccountResquest;
import com.nhatanh.centerlearn.admin.response.AdminAccountResponse;
import com.nhatanh.centerlearn.admin.validator.AccountValidator;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.nhatanh.centerlearn.common.utils.DateFormatter;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Api
@Controller("/api/v1/accounts")
@AllArgsConstructor
public class AdminAccountApiController {
    private final AccountValidator accountValidator;
    private final AccountServiceController accountServiceController;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoPost("/add")
    public ResponseEntity addAccount(
        @RequestBody SaveAccountResquest resquest
        ) {
        System.out.println(resquest.toString());
        this.accountValidator.validate(resquest);
        this.accountServiceController.addAccount(this.requestToModelConverter.toSaveAccountModelConverter(resquest));
        return ResponseEntity.noContent();
    }

    @DoGet("/")
    public PaginationModel<AdminAccountResponse> getAccountByType(
        @RequestParam (value = "status") int status,
        @RequestParam (value = "roleId") long roleId,
        @RequestParam (value = "startDate") LocalDate startDate,
        @RequestParam (value = "endDate") LocalDate endDate,
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size
    ) {
        AccountFilterCriteria criteria = AccountFilterCriteria.builder()
            .status(status)
            .roleId(roleId)
            .startDate(startDate)
            .endDate(endDate)
            .build();
        System.out.println(criteria.toString());
        this.accountValidator.validateCriteriaFilter(criteria);
        PaginationModel<AdminAccountResponse> accountResponsePagination = this.accountServiceController.getAccountByType(
            criteria,
            page,
            size
        );
        return accountResponsePagination;
    }
}
