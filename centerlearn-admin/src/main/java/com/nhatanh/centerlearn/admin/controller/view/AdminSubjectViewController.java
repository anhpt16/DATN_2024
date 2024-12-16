package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.admin.controller.service.SubjectServiceController;
import com.nhatanh.centerlearn.admin.filter.SubjectFilterCriteria;
import com.nhatanh.centerlearn.admin.response.AdminSubjectResponse;
import com.nhatanh.centerlearn.common.enums.SubjectStatus;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/admin/subject")
public class AdminSubjectViewController {
    private final SubjectServiceController subjectServiceController;

    @DoGet("/manage")
    public View initAddSubject(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminSubjectResponse> subjectPaginationResponse = this.subjectServiceController.getSubjectPaginationByFilter(SubjectFilterCriteria.builder().build(), page, size);
        List<SubjectStatus> subjectStatusList = this.subjectServiceController.getAllSubjectStatus();
        return View.builder()
            .addVariable("subjectPagination", subjectPaginationResponse)
            .addVariable("statuses", subjectStatusList)
            .template("/contents/subject/manage_subject")
            .build();
    }

    @DoGet("/list")
    public View initListSubject(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "12") int size
    ) {
        PaginationModel<AdminSubjectResponse> subjectPaginationResponse = this.subjectServiceController.getSubjectPaginationByFilter(SubjectFilterCriteria.builder().build(), page, size);
        List<SubjectStatus> subjectStatusList = this.subjectServiceController.getAllSubjectStatus();
        return View.builder()
            .addVariable("subjectPagination", subjectPaginationResponse)
            .addVariable("statuses", subjectStatusList)
            .template("/contents/subject/list_subject")
            .build();
    }

}
