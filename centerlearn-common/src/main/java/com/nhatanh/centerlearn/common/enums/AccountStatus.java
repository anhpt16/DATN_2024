package com.nhatanh.centerlearn.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {
    ACTIVE(1, "Hoạt động", "#28a745"),
    INACTIVE(2, "Ngừng hoạt động", "#dc3545");

    private int code;
    private String displayName;
    private String colorCode;

    AccountStatus(int code, String displayName, String colorCode) {
        this.code = code;
        this.displayName = displayName;
        this.colorCode = colorCode;
    }

    AccountStatus(int code, String displayName) {
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

    public static String getNameByCode(int code) {
        return Arrays.stream(AccountStatus.values())
            .filter(status -> status.getCode() == code)
            .findFirst()
            .map(Enum::name)
            .orElseThrow(() -> new IllegalArgumentException("No enum constant with code: " + code));
    }

    public static AccountStatus fromString(String text) {
        return Arrays.stream(AccountStatus.values())
            .filter(status -> status.name().equalsIgnoreCase(text))
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
