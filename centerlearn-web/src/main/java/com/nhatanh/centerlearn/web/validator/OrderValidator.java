package com.nhatanh.centerlearn.web.validator;

import com.nhatanh.centerlearn.common.enums.OrderStatus;
import com.nhatanh.centerlearn.common.validator.FormValidator;
import com.nhatanh.centerlearn.web.service.CourseService;
import com.nhatanh.centerlearn.web.service.OrderService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpBadRequestException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@EzySingleton
public class OrderValidator {
    private final FormValidator formValidator;
    private final CourseService courseService;
    private final OrderService orderService;

    public void validate(long accountId, long courseId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException(("Course Not Found"));
            }
        }
        if (this.orderService.getOrderComplete(accountId, courseId, OrderStatus.COMPLETE.name()) != null) {
            throw new HttpBadRequestException("Account Has This Course");
        }
    }

    public void validatePayment(long courseId, long orderId) {
        if (courseId <= 0) {
            throw new HttpBadRequestException("CourseId Invalid");
        } else {
            if (this.courseService.getCourseById(courseId) == null) {
                throw new HttpBadRequestException(("Course Not Found"));
            }
        }
        if (orderId <= 0) {
            throw new HttpBadRequestException("orderId Invalid");
        } else {
            if (this.orderService.getOrderById(orderId) == null) {
                throw new HttpBadRequestException(("Order Not Found"));
            }
        }
    }
}
