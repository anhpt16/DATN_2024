package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum MethodName {
    GET("GET", "#3498db"),
    POST("POST", "#16a085"),
    PUT("PUT", "#f1c40f"),
    DELETE("DELETE", " #e74c3c");

    private String displayName;
    private String colorCode;

    MethodName(String displayName, String colorCode){
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    MethodName(String displayName) {
        this.displayName = displayName;
        this.colorCode = null;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static MethodName fromString(String text) {
        return Arrays.stream(MethodName.values())
            .filter(method -> method.getDisplayName().equalsIgnoreCase(text))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No enum constant with text: " + text));
    }

    @JsonValue
    public Map<String, String> toJson() {
        Map<String, String> json = new HashMap<>();
        json.put("name", this.name());
        json.put("displayName", this.displayName);
        json.put("colorCode", this.colorCode);
        return json;
    }
}
