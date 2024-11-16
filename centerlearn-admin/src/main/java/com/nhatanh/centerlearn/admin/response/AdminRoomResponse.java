package com.nhatanh.centerlearn.admin.response;

import com.nhatanh.centerlearn.common.enums.RoomStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@Builder
public class AdminRoomResponse {
    private final long id;
    private final String name;
    private final int slot;
    private final String description;
    private final String termName;
    private final RoomStatus status;
    private final LocalDate createdAt;
    private final LocalDate updatedAt;
}
