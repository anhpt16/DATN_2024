package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@IdClass(AccountRoleId.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "account_role"
)
public class AccountRole {
    @Id
    @Column(
        name = "account_id"
    )
    private long accountId;
    @Id
    @Column(
        name = "role_id"
    )
    private long roleId;

}
