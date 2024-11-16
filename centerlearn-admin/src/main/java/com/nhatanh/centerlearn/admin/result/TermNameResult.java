package com.nhatanh.centerlearn.admin.result;

import com.tvd12.ezyfox.database.annotation.EzyQueryResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EzyQueryResult
public class TermNameResult {
    private String name;
}
