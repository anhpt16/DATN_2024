package com.nhatanh.centerlearn.common.utils;

import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@EzySingleton
@AllArgsConstructor
public class RequestContext {
    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();
    public static void set(String key, Object value) {
        if (context.get() == null) {
            context.set(new HashMap<>());
        }
        context.get().put(key, value);
    }
    public static Object get(String key) {
        Map<String, Object> currentContext = context.get();
        if (currentContext != null) {
            return currentContext.get(key);
        }
        return null;
    }
    public static void remove(String key) {
        Map<String, Object> currentContext = context.get();
        if (currentContext != null) {
            currentContext.remove(key);
        }
    }
    public static void clear() {
        context.remove();
    }
}
