package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SavePermissionRequest {
    private long roleId;
    private String uriFeature;
    private String uriMethod;
}
