package com.nhatanh.centerlearn.web.response;

import com.nhatanh.centerlearn.common.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
public class OrderDetailResponse {
    private final long id;
    private final String status;
    private final double price;
    private final long courseId;
    private final String courseCode;
    private final String courseName;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String code;
}