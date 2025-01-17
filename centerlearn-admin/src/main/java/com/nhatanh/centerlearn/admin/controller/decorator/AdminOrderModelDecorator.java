package com.nhatanh.centerlearn.admin.controller.decorator;

import com.nhatanh.centerlearn.admin.model.CourseModel;
import com.nhatanh.centerlearn.admin.service.CourseService;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.web.response.OrderDetailResponse;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.tvd12.ezyfox.io.EzyLists.newArrayList;
@EzySingleton
@AllArgsConstructor
public class AdminOrderModelDecorator {
    private final CourseService courseService;

//    public List<OrderDetailResponse> decorateOrderModel(List<OrderModel> orderModels) {
//        Set<Long> courseIds = orderModels.stream()
//            .map(OrderModel::getCourseId)
//            .filter(id -> id > 0)
//            .collect(Collectors.toSet());
//        Map<Long, CourseModel> courseModelMapByIds = courseIds.stream().collect(Collectors.toMap(
//            courseId -> courseId,
//            this.courseService::getCourseById
//        ));
//        List<OrderDetailResponse> orderDetailResponses = newArrayList(orderModels, orderModel -> this.webModelToResponseConverter.toOrderDetailResponse(orderModel, courseModelMapByIds.get(orderModel.getCourseId())));
//        if (orderDetailResponses.isEmpty()) {
//            return Collections.emptyList();
//        }
//        return orderDetailResponses;
//    }
}
