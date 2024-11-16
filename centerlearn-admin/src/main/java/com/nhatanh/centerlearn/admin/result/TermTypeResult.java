package com.nhatanh.centerlearn.admin.result;


import com.tvd12.ezyfox.database.annotation.EzyQueryResult;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@EzyQueryResult
public class TermTypeResult {
    private String type;
}
