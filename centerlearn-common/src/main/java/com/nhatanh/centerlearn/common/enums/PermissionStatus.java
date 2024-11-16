package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum PermissionStatus {
    ACTIVE(2, "Đã phân quyền", "#16a085"),
    INACTIVE(1, "Chưa phân quyền", "#909497"),
    NONE(0, "Không hợp lệ", "#e74c3c");

    private int code;
    private String displayName;
    private String colorCode;

    PermissionStatus(int code, String displayName, String colorCode) {
        this.code = code;
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    PermissionStatus(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
        this.colorCode = null;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static PermissionStatus fromString(String text) {
        return Arrays.stream(PermissionStatus.values())
            .filter(permissionStatus -> permissionStatus.getDisplayName().equalsIgnoreCase(text))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No enum constant with text: " + text));
    }

    @JsonValue
    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("name", this.name());
        json.put("code", this.code);
        json.put("displayName", this.displayName);
        json.put("colorCode", this.colorCode);
        return json;
    }
}
