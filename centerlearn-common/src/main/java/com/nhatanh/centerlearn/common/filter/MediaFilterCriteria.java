package com.nhatanh.centerlearn.common.filter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class MediaFilterCriteria {
    private String name;
    private long ownerAccountId;
    private int sortOrder;
}
