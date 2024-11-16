package com.nhatanh.centerlearn.common.exception;

import java.util.Collections;
import java.util.Map;

public class TokenAuthException extends RuntimeException{
    private final String resource;

    public TokenAuthException(String resource) {
        this(resource, resource + " not found");
    }

    public TokenAuthException(String resource, String message) {
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
