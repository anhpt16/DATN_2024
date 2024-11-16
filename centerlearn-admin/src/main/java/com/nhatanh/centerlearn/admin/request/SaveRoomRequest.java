package com.nhatanh.centerlearn.admin.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SaveRoomRequest {
    private String name;
    private int slot;
    private long base;
    private String description;
}
