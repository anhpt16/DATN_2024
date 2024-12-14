package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum SubjectStatus {
    ACTIVE("ACTIVE", "Hoạt động" ,"#28a745"),
    INACTIVE("INACTIVE", "Ngừng hoạt động", "#dc3545");

    private final String name;
    private final String displayName;
    private final String colorCode;

    SubjectStatus(String name, String displayName, String colorCode) {
        this.name = name;
        this.displayName = displayName;
        this.colorCode = colorCode;
    }
    SubjectStatus(String displayName) {
        this.name = null;
        this.displayName = displayName;
        this.colorCode = null;
    }

    public static SubjectStatus fromString(String text) {
        return Arrays.stream(SubjectStatus.values())
            .filter(status -> status.getName().equalsIgnoreCase(text))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No enum constant with text: " + text));
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public String getName() {
        return name;
    }

    @JsonValue
    public Map<String, String> toJson() {
        Map<String, String> json = new HashMap<>();
        json.put("name", this.name);
        json.put("displayName", this.displayName);
        json.put("colorCode", this.colorCode);
        return json;
    }
}
