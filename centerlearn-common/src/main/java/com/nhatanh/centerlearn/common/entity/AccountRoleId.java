package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AccountRoleId implements Serializable {
    private long accountId;
    private long roleId;
}
