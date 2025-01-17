package com.nhatanh.centerlearn.web.controller.service;

import com.nhatanh.centerlearn.common.converter.ModelToResponseConverter;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.common.response.OrderResponse;
import com.nhatanh.centerlearn.web.controller.decorator.WebCourseModelDecorator;
import com.nhatanh.centerlearn.web.controller.decorator.WebOrderModelDecorate;
import com.nhatanh.centerlearn.web.model.CourseModel;
import com.nhatanh.centerlearn.web.response.OrderDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseDetailResponse;
import com.nhatanh.centerlearn.web.response.WebCourseResponse;
import com.nhatanh.centerlearn.web.response.WebOrderResponse;
import com.nhatanh.centerlearn.web.service.CourseService;
import com.nhatanh.centerlearn.web.service.OrderService;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;

@Service
@AllArgsConstructor
public class OrderServiceController {
    private final OrderService orderService;
    private final CourseService courseService;
    private final ModelToResponseConverter modelToResponseConverter;
    private final WebCourseModelDecorator webCourseModelDecorator;
    private final WebOrderModelDecorate webOrderModelDecorate;

    public OrderResponse addOrder(long accountId, long courseId) {
        CourseModel courseModel = this.courseService.getCourseById(courseId);
        OrderModel orderModel = this.orderService.addOrder(accountId, courseId, courseModel.getPrice());
        return this.modelToResponseConverter.toOrderResponse(orderModel);
    }

    public OrderResponse getOrderById(long id) {
        OrderModel orderModel = this.orderService.getOrderById(id);
        if (orderModel == null) {
            return null;
        }
        return this.modelToResponseConverter.toOrderResponse(orderModel);
    }

    public List<OrderDetailResponse> getOrderSuccessByAccountId(long accountId) {
        List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
        if (orderModels.isEmpty()) {
            return Collections.emptyList();
        }
        return this.webOrderModelDecorate.decorateOrderModel(orderModels);
    }

    public List<WebCourseDetailResponse> getOwnerCourse(long accountId) {
        List<OrderModel> orderModels = this.orderService.getOrderSuccessByAccountId(accountId);
        Set<Long> courseIds = orderModels.stream()
            .map(OrderModel::getCourseId)
            .filter(id -> id > 0)
            .collect(Collectors.toSet());
        List<CourseModel> courseModels = this.courseService.getListCourseByIds(courseIds);
        if (courseModels.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(courseModels, this.webCourseModelDecorator::decorateCourseModel);
    }
}
