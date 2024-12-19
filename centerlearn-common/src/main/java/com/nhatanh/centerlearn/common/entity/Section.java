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
    name = "section"
)
public class Section {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String title;
    private String content;
    @Column(
        name = "creator_id"
    )
    private long creatorId;
    private String status;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    private float priority;
    @Column(
        name = "lesson_id"
    )
    private long lessonId;
}
