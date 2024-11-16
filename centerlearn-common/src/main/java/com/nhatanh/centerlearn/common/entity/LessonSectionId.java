package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LessonSectionId implements Serializable {
    private long lessonId;
    private long sectionId;
}
