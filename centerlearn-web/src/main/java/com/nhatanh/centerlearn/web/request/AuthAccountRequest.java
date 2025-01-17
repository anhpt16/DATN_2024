package com.nhatanh.centerlearn.web.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AuthAccountRequest {
    private String username;
    private String password;
}
