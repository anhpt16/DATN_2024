package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;

    public long getTokenRoleId(String token) {
        String idString = this.jwtUtil.extractRole(token);
        return Long.parseLong(idString);
    }

    public long getTokenAccountId(String token) {
        String idAccount = this.jwtUtil.extractUserId(token);
        return Long.parseLong(idAccount);
    }
}
