package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum RoomStatus {
    ACTIVE("ACTIVE", "#28a745"),
    INACTIVE("INACTIVE", "#dc3545");

    private final String displayName;
    private final String colorCode;

    RoomStatus(String displayName, String colorCode) {
        this.displayName = displayName;
        this.colorCode = colorCode;
    }
    RoomStatus(String displayName) {
        this.displayName = displayName;
        this.colorCode = null;
    }

    public static RoomStatus fromString(String text) {
        return Arrays.stream(RoomStatus.values())
            .filter(status -> status.getDisplayName().equalsIgnoreCase(text))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No enum constant with text: " + text));
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
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
