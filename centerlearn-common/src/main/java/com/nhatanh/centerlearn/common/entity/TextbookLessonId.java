package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class TextbookLessonId implements Serializable {
    private long textbookId;
    private long lessonId;
}
