package com.nhatanh.centerlearn.common.utils;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;

import java.util.List;
import java.util.regex.Pattern;

@EzySingleton
public class UriMatcher {
    public boolean isUriMatch(String requestUri, List<String> uriPatterns) {
        // Sử dụng Stream API để kiểm tra từng URI pattern trong danh sách
        return uriPatterns.stream().anyMatch(pattern -> {
            // Chuyển đổi pattern {id} thành regex cho phép bất kỳ giá trị nào
            String regexPattern = convertUriPatternToRegex(pattern);
            // So sánh requestUri với regexPattern
            return Pattern.matches(regexPattern, requestUri);
        });
    }

    // Chuyển đổi URI pattern chứa {id} thành regex
    private String convertUriPatternToRegex(String uriPattern) {
        // Thay thế tất cả các phần {id} bằng regex cho phép bất kỳ giá trị nào
        return uriPattern.replaceAll("\\{[^/]+\\}", "[^/]+");
    }
}
