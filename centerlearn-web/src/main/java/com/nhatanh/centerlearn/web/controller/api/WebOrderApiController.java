package com.nhatanh.centerlearn.web.controller.api;

import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.response.OrderResponse;
import com.nhatanh.centerlearn.common.utils.RequestContext;
import com.nhatanh.centerlearn.web.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.web.controller.service.OrderServiceController;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.nhatanh.centerlearn.web.service.OrderService;
import com.nhatanh.centerlearn.web.validator.OrderValidator;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.Api;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoPost;
import com.tvd12.ezyhttp.server.core.annotation.PathVariable;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;
import org.eclipse.jetty.http.HttpStatus;

import java.util.Optional;

@AllArgsConstructor
@Api
@Controller("/api/v1/order")
public class WebOrderApiController {
    private final OrderValidator orderValidator;
    private final OrderServiceController orderServiceController;
    private final CourseServiceController courseServiceController;

    @DoPost("/course/{courseId}")
    public ResponseEntity postOrder(
        @PathVariable long courseId
    ) {
        //validate
        Long accountId = Optional.ofNullable(RequestContext.get("accountId"))
            .map(account -> (Long) account)
            .orElseThrow(() -> new HttpUnauthorizedException("User Invalid"));
        this.orderValidator.validate(accountId, courseId);
        OrderResponse orderResponse = this.orderServiceController.addOrder(accountId, courseId);
        if (orderResponse == null) {
            return ResponseEntity.badRequest("Fail to create Order");
        }
        CourseModel courseModel = this.courseServiceController.getCourseById(courseId);

        String redirectUrl = String.format("/payment?orderId=%d&courseId=%d&lang=vi", orderResponse.getId(), courseId);
        return ResponseEntity.ok(redirectUrl);
    }
}

