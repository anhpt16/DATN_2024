package com.nhatanh.centerlearn.admin.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SaveRoomModel {
    private final String name;
    private final String description;
    private final int slot;
    private final long termId;
    private final String status;
}
