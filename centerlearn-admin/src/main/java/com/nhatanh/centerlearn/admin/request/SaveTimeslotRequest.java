package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveTimeslotRequest {
    private int period;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String description;
}
