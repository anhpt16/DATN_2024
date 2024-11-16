package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "timeslot"
)
public class Timeslot {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private int period;
    @Column(
        name = "time_start"
    )
    private LocalTime timeStart;
    @Column(
        name = "time_end"
    )
    private LocalTime timeEnd;
    private String status;
    private String description;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
}
