package com.nhatanh.centerlearn.common.exception;

import java.util.Collections;
import java.util.Map;

public class AccessDeniedException extends RuntimeException{
    private final String resource;

    public AccessDeniedException(String resource) {
        this(resource, resource + " is denied");
    }

    public AccessDeniedException(String resource, String message) {
        super(message);
        this.resource = resource;
    }

    public Map<String, String> getResponseData() {
        return Collections.singletonMap(this.resource, "isDenied");
    }

    public String getResource() {
        return this.resource;
    }
}
