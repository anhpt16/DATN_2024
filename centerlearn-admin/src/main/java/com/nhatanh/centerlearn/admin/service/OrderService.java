package com.nhatanh.centerlearn.admin.service;

import com.nhatanh.centerlearn.admin.repo.OrderRepository;
import com.nhatanh.centerlearn.common.entity.Order;
import com.nhatanh.centerlearn.common.model.OrderModel;
import com.sun.org.apache.xpath.internal.operations.Or;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

//    public List<OrderModel> getAllOrderModel() {
//        List<Order> orders = this.orderRepository.findAll();
//
//    }
}
