package com.nhatanh.centerlearn.admin.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class AccountFilterCriteria {
    private int status;
    private long roleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
