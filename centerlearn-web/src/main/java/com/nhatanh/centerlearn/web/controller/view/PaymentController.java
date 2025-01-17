package com.nhatanh.centerlearn.web.controller.view;

import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.common.response.OrderResponse;
import com.nhatanh.centerlearn.web.controller.service.CourseServiceController;
import com.nhatanh.centerlearn.web.controller.service.OrderServiceController;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.validator.OrderValidator;
import com.tvd12.ezyhttp.server.core.annotation.Controller;
import com.tvd12.ezyhttp.server.core.annotation.DoGet;
import com.tvd12.ezyhttp.server.core.annotation.RequestParam;
import com.tvd12.ezyhttp.server.core.view.View;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class PaymentController {
    private final CourseServiceController courseServiceController;
    private final OrderServiceController orderServiceController;
    private final OrderValidator orderValidator;

    @DoGet("/payment")
    public View initPayment(
        @RequestParam (value = "orderId") long orderId,
        @RequestParam (value = "courseId") long courseId
    ) {
        this.orderValidator.validatePayment(courseId, orderId);
        CourseModel courseModel = this.courseServiceController.getCourseById(courseId);
        OrderResponse orderModel = this.orderServiceController.getOrderById(orderId);
        return View.builder()
            .addVariable("course", courseModel)
            .addVariable("order", orderModel)
            .template("payment")
            .build();
    }
}
