package com.nhatanh.centerlearn.admin.controller.api;

import com.nhatanh.centerlearn.admin.controller.service.TextbookServiceController;
import com.nhatanh.centerlearn.admin.converter.AdminRequestToModelConverter;
import com.nhatanh.centerlearn.admin.filter.TextbookFilterCriteria;
import com.nhatanh.centerlearn.admin.request.AddTextbookRequest;
import com.nhatanh.centerlearn.admin.request.SaveTextbookRequest;
import com.nhatanh.centerlearn.admin.response.AdminTextbookResponse;
import com.nhatanh.centerlearn.admin.validator.TextbookValidator;
import com.nhatanh.centerlearn.common.model.PaginationModel;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

@Api
@Controller("/api/v1/textbook")
@AllArgsConstructor
/*
    Lấy danh sách giáo trình theo điều kiện lọc
    Tạo một giáo trình
    Sửa một giáo trình theo id
    Xem chi tiết một giáo trình theo id
    Thêm bài học cho một giáo trình -> Thêm đề mục cho bài học -> Thêm bài tập cho bài học
 */
public class AdminTextbookApiController {
    private final TextbookServiceController textbookServiceController;
    private final TextbookValidator textbookValidator;
    private final AdminRequestToModelConverter adminRequestToModelConverter;

    @DoGet
    public PaginationModel<AdminTextbookResponse> getTextbookPaginationByFilter(
        @RequestParam (value = "page", defaultValue = "0") int page,
        @RequestParam (value = "size", defaultValue = "10") int size,
        @RequestParam (value = "id") long id,
        @RequestParam (value = "name") String name,
        @RequestParam (value = "author") String author,
        @RequestParam (value = "status") String status,
        @RequestParam (value = "sortOrder", defaultValue = "2") int sortOrder,
        @RequestParam (value = "subjectId") long subjectId
    ) {
        TextbookFilterCriteria criteria = TextbookFilterCriteria.builder()
            .id(id)
            .name(name)
            .author(author)
            .status(status)
            .sortOrder(sortOrder)
            .subjectId(subjectId)
            .build();
        this.textbookValidator.validate(criteria);
        PaginationModel<AdminTextbookResponse> textbookPaginationResponse = this.textbookServiceController.getTextbookPaginationByFilter(criteria, page, size);
        return textbookPaginationResponse;
    }

    @DoGet("/{id}")
    public ResponseEntity getTextbookById(
        @PathVariable long id
    ) {
        AdminTextbookResponse adminTextbookResponse = this.textbookServiceController.getTextbookById(id);
        if (adminTextbookResponse == null) {
            return ResponseEntity.notFound("Textbook with id: " + id + " not found");
        }
        return ResponseEntity.ok(adminTextbookResponse);
    }

    @DoPost
    public ResponseEntity addTextbook(
        @RequestBody AddTextbookRequest request
    ) {
        // validate
        this.textbookValidator.validate(request);
        this.textbookServiceController.addTextbook(this.adminRequestToModelConverter.toAddTextbookModel(request));
        return ResponseEntity.noContent();
    }

    @DoPut("/{id}")
    public ResponseEntity updateTextbookById(
        @PathVariable long id,
        @RequestBody SaveTextbookRequest request
        ) {
        // validate
        this.textbookValidator.validate(request, id);
        this.textbookValidator.validateNull(request);
        this.textbookServiceController.updatedTextbookById(this.adminRequestToModelConverter.toSaveTextbookModel(request, id));
        return ResponseEntity.noContent();
    }

    @DoPost("/{id}/lesson")
    public ResponseEntity addTextbookLesson(

    ) {

        return ResponseEntity.noContent();
    }

    /* Common (Quản trị + Quản lý + Giảng viên)
        Thêm bài học -> Thêm đề mục cho bài học -> Thêm bài tập cho bài học
     */
}
