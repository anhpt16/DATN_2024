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
    name = "course_subject"
)
public class CourseSubject {
    @Id
    @GeneratedValue(
        strategy = GenerationType.IDENTITY
    )
    private long id;
    @Column(
        name = "course_id"
    )
    private long courseId;
    @Column(
        name = "subject_id"
    )
    private long subjectId;
    @Column(
        name = "textbook_id"
    )
    private long textbook_id;
}
