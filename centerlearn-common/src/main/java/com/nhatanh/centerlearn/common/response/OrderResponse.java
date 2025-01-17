package com.nhatanh.centerlearn.common.response;

import com.nhatanh.centerlearn.common.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class OrderResponse {
    private final long id;
    private final long accountId;
    private final OrderStatus status;
    private final long managerId;
    private final double price;
    private final long courseId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String code;
}
