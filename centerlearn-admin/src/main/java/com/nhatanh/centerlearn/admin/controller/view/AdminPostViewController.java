package com.nhatanh.centerlearn.admin.controller.view;

import com.nhatanh.centerlearn.admin.controller.service.TermServiceController;
import com.nhatanh.centerlearn.admin.response.AdminTermResponse;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Controller("/post")
public class AdminPostViewController {
    private final TermServiceController termServiceController;

    @DoGet("/term")
    public View initTerm(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        PaginationModel<AdminTermResponse> adminTermResponses = termServiceController.getAllTermPagination(page, size);
        List<String> termTypes = termServiceController.getTypesByTermTypes();
        return View.builder()
            .addVariable("terms", adminTermResponses)
            .addVariable("termTypes", termTypes)
            .template("/contents/post/post-term")
            .build();
    }
    @DoGet("/writing")
    public View initPostWriting() {
        return View.builder()
            .template("/contents/post/post-writing")
            .build();
    }
    @DoGet("/list")
    public View initPostList() {
        return View.builder()
            .template("/contents/post/post-list")
            .build();
    }
    @DoGet("/review")
    public View initPostReview() {
        return View.builder()
            .template("/contents/post/post-review")
            .build();
    }
    @DoGet("/draft")
    public View initPostDraft() {
        return View.builder()
            .template("/contents/post/post-draft")
            .build();
    }
}
