package com.nhatanh.centerlearn.common.exception;

import com.tvd12.ezyfox.exception.NotFoundException;
import com.tvd12.ezyfox.util.EzyLoggable;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import com.tvd12.ezyhttp.core.response.ResponseEntity;
import com.tvd12.ezyhttp.server.core.annotation.ExceptionHandler;
import com.tvd12.ezyhttp.server.core.annotation.TryCatch;

@ExceptionHandler
public class GlobalExceptionHandler extends EzyLoggable {
    @TryCatch(IllegalArgumentException.class)
    public ResponseEntity handleException(
        IllegalArgumentException e
    ) {
        logger.info("invalid argument: {}", e.getMessage());
        return ResponseEntity.badRequest(e.getMessage());
    }
    @TryCatch(NotFoundException.class)
    public ResponseEntity handleException(
        NotFoundException e
    ) {
        logger.info("not found: {}", e.getMessage());
        return ResponseEntity.notFound(e.getMessage());
    }

    @TryCatch(TokenAuthException.class)
    public ResponseEntity handleException(
        TokenAuthException e
    ) {
        logger.info("invalid: " + e.getMessage());
        return ResponseEntity.badRequest(e.getMessage());
    }

    @TryCatch(AccessDeniedException.class)
    public ResponseEntity handleException(
        AccessDeniedException e
    ) {
        logger.info("denied: " + e.getMessage());
        return ResponseEntity.builder()
            .status(403)
            .body(e.getMessage())
            .build();
    }

    @TryCatch(HttpUnauthorizedException.class)
    public ResponseEntity handleException(
        HttpUnauthorizedException e
    ) {
        logger.info("Global Exception: " + e.getMessage());
        return ResponseEntity.builder()
            .status(401)
            .body("Global Exception: " + e.getMessage())
            .build();
    }

    @TryCatch(HttpTokenExpiration.class)
    public ResponseEntity handleExeption(
        HttpTokenExpiration e
    ) {
        logger.info("Global Exception: " + e.getMessage());
        return ResponseEntity.builder()
            .header("Location", "/admin/login?lang=vi")
            .status(302)
            .body("Global Exception: " + e.getMessage())
            .build();
    }
}
