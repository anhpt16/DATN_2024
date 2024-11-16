package com.nhatanh.centerlearn.common.model;

import com.nhatanh.centerlearn.common.enums.MethodName;
import com.nhatanh.centerlearn.common.enums.PermissionStatus;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UriModel {
    private String uriPath;
    private MethodName uriMethod;
    private PermissionStatus status;
}

