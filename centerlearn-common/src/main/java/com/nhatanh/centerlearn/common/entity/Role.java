package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "role"
)
public class Role {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    @Column(
        name = "display_name"
    )
    private String displayName;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
}
