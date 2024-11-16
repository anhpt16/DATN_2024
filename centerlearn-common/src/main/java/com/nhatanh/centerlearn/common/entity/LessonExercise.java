package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@IdClass(LessonExerciseId.class)
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(
    name = "lesson_exercise"
)
public class LessonExercise {
    @Id
    @Column(
        name = "lesson_id"
    )
    private long lessonId;
    @Id
    @Column(
        name = "exercise_id"
    )
    private long exerciseId;
}
