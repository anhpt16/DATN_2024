package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveRoleRequest {
    private String name;
    private String displayName;
}
