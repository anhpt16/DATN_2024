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
    name = "textbook"
)
public class Textbook {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    private String description;
    private String author;
    private String url;
    private String status;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    private String slug;
}
