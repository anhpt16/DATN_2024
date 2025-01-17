package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum CourseType {
    ONLINE("ONLINE", "Trực tuyến" ,"#28a745"),
    OFFLINE("OFFLINE", "Trực tiếp", "#dc3545");

    private final String name;
    private final String displayName;
    private final String colorCode;

    CourseType(String name, String displayName, String colorCode) {
        this.name = name;
        this.displayName = displayName;
        this.colorCode = colorCode;
    }
    CourseType(String displayName) {
        this.name = null;
        this.displayName = displayName;
        this.colorCode = null;
    }

    public static CourseType fromString(String text) {
        return Arrays.stream(CourseType.values())
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
