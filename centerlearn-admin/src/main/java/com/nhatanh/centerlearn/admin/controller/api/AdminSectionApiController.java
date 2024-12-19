package com.nhatanh.centerlearn.admin.controller.api;

import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

@Api
@Controller("/api/v1/section")
@AllArgsConstructor
public class AdminSectionApiController {


    // Sửa một đề mục
    @DoPost("/{id}")
    public ResponseEntity updateSection(
        @PathVariable long id

    ) {
        return ResponseEntity.noContent();
    }

    // Xem một đề mục
    @DoGet("/{id}")
    public ResponseEntity getSectionById(
        @PathVariable long id
    ) {
        return ResponseEntity.noContent();
    }

    // Xóa một đề mục
    @DoDelete("/{id}")
    public ResponseEntity deleteSectionById(
        @PathVariable long id
    ) {
        return ResponseEntity.noContent();
    }
}
