package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "room"
)
public class Room {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    private String description;
    private int slot;
    @Column(
        name = "term_id"
    )
    private long termId;
    private String status;
    @Column(
        name = "created_at"
    )
    private LocalDate createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDate updatedAt;
}
