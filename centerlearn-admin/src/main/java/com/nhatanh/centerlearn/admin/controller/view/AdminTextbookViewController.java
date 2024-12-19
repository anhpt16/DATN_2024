package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.admin.controller.service.SubjectServiceController;
import com.nhatanh.centerlearn.admin.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.response.AdminSubjectShortResponse;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.common.enums.TextbookStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/admin/textbook")
public class AdminTextbookViewController {
    private final TextbookServiceController textbookServiceController;
    private final SubjectServiceController subjectServiceController;

    @DoGet("/{slug}")
    public View initDetailTextbook(
        @PathVariable String slug
    ) {
        AdminTextbookResponse adminTextbookResponse = this.textbookServiceController.getTextbookBySlug(slug);

        return View.builder()
            .addVariable("textbook", adminTextbookResponse)

            .template("/contents/textbook/detail_textbook")
            .build();
    }

    @DoGet("/list")
    public View initTextBook(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminTextbookResponse> textbookPaginationResponse = this.textbookServiceController.getTextbookPaginationByFilter(
            TextbookFilterCriteria.builder().build(),
            page,
            size
        );
        List<TextbookStatus> textbookStatusList = this.textbookServiceController.getAllTextbookStatus();
        List<AdminSubjectShortResponse> subjectShortResponses = this.subjectServiceController.getAllSubjectShort();
        return View.builder()
            .addVariable("textbookPagination", textbookPaginationResponse)
            .addVariable("statuses", textbookStatusList)
            .addVariable("subjects", subjectShortResponses)
            .template("/contents/textbook/list_textbook")
            .build();
    }

    @DoGet("/manage")
    public View initManageTextbook(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "12") int size
    ) {
        PaginationModel<AdminTextbookResponse> textbookPaginationResponse = this.textbookServiceController.getTextbookPaginationByFilter(
            TextbookFilterCriteria.builder().build(),
            page,
            size
        );
        List<TextbookStatus> textbookStatusList = this.textbookServiceController.getAllTextbookStatus();
        List<AdminSubjectShortResponse> subjectShortResponses = this.subjectServiceController.getAllSubjectShort();
        return View.builder()
            .addVariable("textbookPagination", textbookPaginationResponse)
            .addVariable("statuses", textbookStatusList)
            .addVariable("subjects", subjectShortResponses)
            .template("/contents/textbook/manage_textbook")
            .build();
    }

    @DoGet("/detail")
    public View initDetailTextbook(

    ) {

        return View.builder()
            .template("/contents/textbook/detail_textbook")
            .build();
    }
}
