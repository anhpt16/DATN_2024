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
    name = "course"
)
public class Course {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String code;
    @Column(
        name = "display_name"
    )
    private String displayName;
    @Column(
        name = "course_type"
    )
    private String courseType;
    private int duration;
    private String description;
    private String status;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    @Column(
        name = "creator_id"
    )
    private long creatorId;
    @Column(
        name = "image_id"
    )
    private long imageId;
    private String slug;
    private double price;
    @Column(
        name = "manage_id"
    )
    private long managerId;
}
