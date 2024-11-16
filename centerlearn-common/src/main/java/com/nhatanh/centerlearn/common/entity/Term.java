package com.nhatanh.centerlearn.common.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "term"
)
public class Term {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    private String name;
    private String slug;
    @Column(
        name = "term_type"
    )
    private String termType;
    @Column(
        name = "parent_id"
    )
    private long parentId;
    private String description;
}

