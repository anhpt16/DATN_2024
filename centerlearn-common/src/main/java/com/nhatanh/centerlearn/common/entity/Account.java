package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "account"
)
public class Account {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String username;
    private String password;
    @Column(
        name = "display_name"
    )
    private String displayName;
    private String email;
    private String phone;
    @Column(
        name = "avatar_image_id"
    )
    private long avatarImageId;
    private String status;
    @Column(
        name = "creator_id"
    )
    private long creatorId;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
}
