package com.nhatanh.centerlearn.web.service;

import com.nhatanh.centerlearn.common.converter.EntityToModelConverter;
import com.nhatanh.centerlearn.common.converter.ModelToEntityConverter;
import com.nhatanh.centerlearn.common.entity.Order;
import com.nhatanh.centerlearn.common.enums.OrderStatus;
import com.nhatanh.centerlearn.common.exception.FailedCreationException;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.nhatanh.centerlearn.web.repo.OrderRepository;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;
import static com.tvd12.ezyfox.io.EzyLists.newArrayList;


@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EntityToModelConverter entityToModelConverter;
    private final ModelToEntityConverter modelToEntityConverter;

    public OrderModel getOrderComplete(long accountId, long courseId, String status) {
        Order order = this.orderRepository.findOrderComplete(accountId, courseId, status);
        if (order == null) {
            return null;
        }
        return this.entityToModelConverter.toOrderModel(order);
    }

    public OrderModel getOrderById(long id) {
        Order order = this.orderRepository.findById(id);
        if (order == null) {
            return null;
        }
        return this.entityToModelConverter.toOrderModel(order);
    }

    public OrderModel addOrder(long accountId, long courseId, double price) {
        Order order = this.modelToEntityConverter.toOrderEntityConverter(accountId, courseId, price);
        this.orderRepository.save(order);
        if (order.getId() == 0) {
            throw new FailedCreationException("Fail to create Order");
        }
        return this.entityToModelConverter.toOrderModel(order);
    }

    public List<OrderModel> getOrderSuccessByAccountId(long accountId) {
        List<Order> orders = this.orderRepository.findOrdersByAccountIdAndStatus(accountId, OrderStatus.COMPLETE.name());
        if (orders.isEmpty()) {
            return Collections.emptyList();
        }
        return newArrayList(orders, this.entityToModelConverter::toOrderModel);
    }
}
