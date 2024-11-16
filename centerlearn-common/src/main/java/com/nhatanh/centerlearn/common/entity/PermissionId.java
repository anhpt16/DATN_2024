package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PermissionId implements Serializable {
    private long roleId;
    private String featureUri;
    private String featureMethod;
}
