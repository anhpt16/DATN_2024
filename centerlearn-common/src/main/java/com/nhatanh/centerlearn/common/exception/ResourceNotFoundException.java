package com.nhatanh.centerlearn.common.exception;

import java.util.Collections;
import java.util.Map;

public class ResourceNotFoundException extends RuntimeException{
    private final String resource;

    public ResourceNotFoundException(String resource) {
        this(resource, resource + " not found");
    }

    public ResourceNotFoundException(String resource, String message) {
        super(message);
        this.resource = resource;
    }

    public Map<String, String> getResponseData() {
        return Collections.singletonMap(this.resource, "notFound");
    }

    public String getResource() {
        return this.resource;
    }
}
