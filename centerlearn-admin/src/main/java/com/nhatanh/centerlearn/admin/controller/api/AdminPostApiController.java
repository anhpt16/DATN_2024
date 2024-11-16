package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.request.SaveTermRequest;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.admin.response.AdminTermSuggestionResponse;
import com.nhatanh.centerlearn.admin.service.TermService;
import com.nhatanh.centerlearn.admin.validator.FormValidator;
import com.nhatanh.centerlearn.admin.validator.TermValidator;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

@Api
@Controller("/api/v1/terms")
@AllArgsConstructor
public class AdminPostApiController {
    private final TermServiceController termServiceController;
    private final TermService termService;
    private final TermValidator termValidator;
    private final FormValidator formValidator;
    private final AdminRequestToModelConverter requestToModelConverter;

    @DoGet("/by-term-type")
    public PaginationModel<AdminTermResponse> getTermsPagination(
        @RequestParam("type") String type,
        @RequestParam("page") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminTermResponse> termsByType = formValidator.validateBlank(type)
            ? termServiceController.getAllTermPagination(page, size)
            : termServiceController.getTermsByTypePagination(type, page, size);
        return termsByType;
    }

    @DoGet("/by-name")
    public AdminTermSuggestionResponse getSuggestionsByKeyword(
        @RequestParam("keyword") String keyword
    ) {
        AdminTermSuggestionResponse response = termServiceController.getSuggestions(keyword);
        return response;
    }

    @DoPost("/")
    public ResponseEntity postTerm(
        @RequestBody SaveTermRequest termRequest
    ) {
        System.out.println(termRequest.toString());
        this.termValidator.validate(termRequest);
        this.termService.addTerm(this.requestToModelConverter.toSaveTermModelConverter(termRequest));
        return ResponseEntity.noContent();
    }

    @DoPut("/{id}")
    public ResponseEntity updateTerm(
        @PathVariable long id,
        @RequestBody SaveTermRequest request
    ) {
        System.out.println(request.toString());
        this.termValidator.validate(request, id);
        this.termService.updateTermById(id, this.requestToModelConverter.toSaveTermModelConverter(request));
        return ResponseEntity.noContent();
    }

    @DoDelete("/{id}")
    public ResponseEntity deleteTerm(
        @PathVariable long id
    ) {
        System.out.println(id);
        this.termService.deleteTermById(id);
        return ResponseEntity.noContent();
    }

}
