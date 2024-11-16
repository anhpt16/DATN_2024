package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@IdClass(SubjectTextbookId.class)
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "subject_textbook"
)
public class SubjectTextbook {
    @Id
    @Column(
        name = "subject_id"
    )
    private long subjectId;
    @Id
    @Column(
        name = "textbook_id"
    )
    private long textbookId;
}
