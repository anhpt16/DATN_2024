package com.nhatanh.centerlearn.common.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class OrderModel {
    private final long id;
    private final long accountId;
    private final String status;
    private final long managerId;
    private final double price;
    private final long courseId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String code;
}
