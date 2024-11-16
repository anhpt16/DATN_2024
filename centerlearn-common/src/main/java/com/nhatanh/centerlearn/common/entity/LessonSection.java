package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@IdClass(LessonSectionId.class)
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "lesson_section"
)
public class LessonSection {
    @Id
    @Column(
        name = "lesson_id"
    )
    private long lessonId;
    @Id
    @Column(
        name = "section_id"
    )
    private long sectionId;
}
