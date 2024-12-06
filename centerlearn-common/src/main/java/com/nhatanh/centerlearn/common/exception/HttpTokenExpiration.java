package com.nhatanh.centerlearn.common.exception;

import com.tvd12.ezyhttp.core.exception.HttpRequestException;

public class HttpTokenExpiration extends HttpRequestException {

    public HttpTokenExpiration(Object data) {
        super(302, data);
    }
}
