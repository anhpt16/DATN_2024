package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@IdClass(TextbookLessonId.class)
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "textbook_lesson"
)
public class TextbookLesson {
    @Id
    @Column(
        name = "textbook_id"
    )
    private long textbookId;
    @Id
    @Column(
        name = "lesson_id"
    )
    private long lessonId;
    private float priority;
}
