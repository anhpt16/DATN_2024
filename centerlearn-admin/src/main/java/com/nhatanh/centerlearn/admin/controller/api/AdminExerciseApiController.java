package com.nhatanh.centerlearn.admin.controller.api;

import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

@Api
@Controller("/api/v1/admin/exercise")
@AllArgsConstructor
public class AdminExerciseApiController {


    // Sửa một bài tập
    @DoPut("/{id}")
    public ResponseEntity updateExercise(
        @PathVariable long id
    ) {
        return ResponseEntity.noContent();
    }

    // Xem thông tin bài tập
    @DoGet("/{id}")
    public ResponseEntity getExerciseById(
        @PathVariable long id
    ) {
        return ResponseEntity.noContent();
    }
}
