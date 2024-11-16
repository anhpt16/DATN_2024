package com.nhatanh.centerlearn.admin.filter;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PermissionFilterCriteria {
    private Long roleId;
    private Integer permissonStatus;
    private String method;
}
