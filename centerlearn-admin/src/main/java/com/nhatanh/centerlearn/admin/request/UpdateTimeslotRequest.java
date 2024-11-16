package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UpdateTimeslotRequest {
    private int period;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private String status;
    private String description;
}
