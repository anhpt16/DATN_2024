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
    name = "media"
)
public class Media {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    private String url;
    @Column(
        name = "media_type"
    )
    private String mediaType;
    @Column(
        name = "owner_account_id"
    )
    private long ownerAccountId;
    private String description;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    @Column(
       name = "file_size"
    )
    private long fileSize;
}
