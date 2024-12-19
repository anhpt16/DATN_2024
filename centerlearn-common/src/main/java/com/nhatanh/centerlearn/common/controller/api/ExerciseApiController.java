package com.nhatanh.centerlearn.common.controller.api;

import com.nhatanh.centerlearn.common.controller.controller.ExerciseServiceController;
import com.nhatanh.centerlearn.common.converter.RequestToModelConverter;
import com.nhatanh.centerlearn.common.model.UpdateExerciseModel;
import com.nhatanh.centerlearn.common.request.AddExerciseRequest;
import com.nhatanh.centerlearn.common.request.UpdateExerciseRequest;
import com.nhatanh.centerlearn.common.response.ExerciseResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.common.validator.ExerciseValidator;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.*;
import lombok.AllArgsConstructor;

import java.util.Optional;

@Api
@Controller("/api/v1/exercise")
@AllArgsConstructor
public class ExerciseApiController {
    private final ExerciseValidator exerciseValidator;
    private final ExerciseServiceController exerciseServiceController;
    private final RequestToModelConverter requestToModelConverter;
    // Thêm bài tập
    @DoPost
    public ResponseEntity addExercise(
        @RequestBody AddExerciseRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validate(request, accountId);
        this.exerciseServiceController.addExercise(this.requestToModelConverter.toAddExerciseModel(request, accountId));
        return ResponseEntity.noContent();
    }
    // Xem thông tin bài tập
    @DoGet("/{id}")
    public ResponseEntity getExerciseById(
        @PathVariable long id
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validate(id, accountId);
        ExerciseResponse exerciseResponse = this.exerciseServiceController.getExerciseById(id);
        if (exerciseResponse == null) {
            return ResponseEntity.notFound("Exercise with id:" + id + " Not Found");
        }
        return ResponseEntity.ok(exerciseResponse);
    }

    // Sửa thông tin bài tập
    @DoPut("/{id}")
    public ResponseEntity updateExerciseById(
        @PathVariable long id,
        @RequestBody UpdateExerciseRequest request
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.exerciseValidator.validate(request, id, accountId);
        this.exerciseValidator.validateNull(request);
        this.exerciseServiceController.updateExercise(this.requestToModelConverter.toUpdateExerciseModel(request, id));
        return ResponseEntity.noContent();
    }
}
