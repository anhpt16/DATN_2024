package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@IdClass(PermissionId.class)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "permission"
)
public class Permission {
    @Id
    @Column(
        name = "role_id"
    )
    private long roleId;

    @Id
    @Column(
        name = "feature_uri"
    )
    private String featureUri;

    @Id
    @Column(
        name = "feature_method"
    )
    private String featureMethod;

    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
}
