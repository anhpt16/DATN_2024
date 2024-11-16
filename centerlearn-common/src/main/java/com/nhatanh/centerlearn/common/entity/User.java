package com.nhatanh.centerlearn.common.entity;

import com.tvd12.ezyfox.io.EzyStrings;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name = "ezy_users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String uuid;

    private String username;

    @Column(name = "display_name")
    private String displayName;

    private String password;

    private String email;

    private String phone;

    private String url;

    private String roleName;

    @Column(name = "avatar_image_id")
    private long avatarImageId;

    @Column(name = "cover_image_id")
    private long coverImageId;

    private String status;

    @Column(name = "activation_key")
    private String activationKey;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public String getName() {
        return EzyStrings.isBlank(displayName) ? username : displayName;
    }
}