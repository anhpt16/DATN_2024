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
    name = "orders"
)
public class Order {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(
        name = "account_id"
    )
    private long accountId;
    @Column(
        name = "manager_id"
    )
    private long managerId;
    private String status;
    @Column(
        name = "total_price"
    )
    private double totalPrice;
    @Column(
        name = "course_id"
    )
    private long courseId;
    @Column(
        name = "created_at"
    )
    private LocalDateTime createdAt;
    @Column(
        name = "updated_at"
    )
    private LocalDateTime updatedAt;
    private String code;
}
