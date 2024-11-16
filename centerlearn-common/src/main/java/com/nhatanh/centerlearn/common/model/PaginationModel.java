package com.nhatanh.centerlearn.common.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PaginationModel<T> {
    private List<T> items;
    private long totalPage;
    private long currentPage;
}
