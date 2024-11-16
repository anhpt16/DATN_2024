package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveAccountResquest {
    private String username;
    private String password;
    private String displayName;
    private String email;
    private String phoneNumber;
    private long roleId;
}
