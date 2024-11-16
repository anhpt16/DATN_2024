package com.nhatanh.centerlearn.common.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class SubjectTextbookId implements Serializable {
    private long subjectId;
    private long textbookId;
}
