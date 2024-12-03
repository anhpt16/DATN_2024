package com.nhatanh.centerlearn.common.service;

import com.nhatanh.centerlearn.common.utils.JWTUtil;
import com.tvd12.ezyhttp.server.core.annotation.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class TokenService {
    private final JWTUtil jwtUtil;

    public List<Long> getTokenRoleId(String token) {
        List<Long> roleIds = this.jwtUtil.extractRole(token);
        return roleIds;
    }

    public long getTokenAccountId(String token) {
        String idAccount = this.jwtUtil.extractUserId(token);
        return Long.parseLong(idAccount);
    }
}
