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
    name = "lesson"
)
public class Lesson {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String title;
    private String description;
    @Column(
        name = "creator_id"
    )
    private long creatorId;
    private String status;
    private String note;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    @Column(
        name = "user_term_id"
    )
    private long userTermId;
}
