package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.TimeslotStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class AdminTimeslotResponse {
    private final long id;
    private final int period;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;
    private final TimeslotStatus status;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
