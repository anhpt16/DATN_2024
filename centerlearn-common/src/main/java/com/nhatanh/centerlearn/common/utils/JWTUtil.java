package com.nhatanh.centerlearn.common.utils;

import com.nhatanh.centerlearn.common.exception.HttpTokenExpiration;
import com.nhatanh.centerlearn.common.exception.TokenAuthException;
import com.nhatanh.centerlearn.common.model.AccountRoleModel;
import com.nhatanh.centerlearn.common.service.AccountRoleService;
import com.tvd12.ezyfox.bean.annotation.EzySingleton;
import com.tvd12.ezyhttp.core.exception.HttpUnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@EzySingleton
@AllArgsConstructor
@Getter
public class JWTUtil {
    private final AccountRoleService accountRoleService;
    // Sử dụng SecretKey để ký token
    private final SecretKey secretKey = Keys.hmacShaKeyFor("Yp9rU8vGdH2dL7WqX3rM5tA9sV8jW3bE".getBytes()); // Đảm bảo khóa có độ dài phù hợp với thuật toán HMAC


    // Tạo JWT token
    public String generateToken(long userId, List<Long> roleIds) {
        return Jwts.builder()
            .setSubject(String.valueOf(userId))
            .claim("role", roleIds)
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token có hiệu lực trong 1 ngày
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    // Lấy thông tin từ JWT token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Lấy userId từ token
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject); // Trả về userId dưới dạng chuỗi
    }

    // Lấy role từ token
    public List<Long> extractRole(String token) {
        return extractClaim(token, claims -> {
            // Lấy danh sách "role" và ép kiểu mỗi phần tử thành Long
            List<Integer> roleList = claims.get("role", List.class);
            return roleList.stream()
                .map(Integer::longValue)  // Ép kiểu Integer thành Long
                .collect(Collectors.toList());
        });
    }

    // Xác thực token hợp lệ
    public boolean validateToken(String token) {
        if (isTokenExpired(token)) {
            return false;
        }
        String tokenUserId = extractUserId(token);
        List<Long> tokenUserRoles = extractRole(token);
        long userId = Long.parseLong(tokenUserId);
        List<Long> roleIdsByAccountId = this.accountRoleService.getRoleIdsByAccountId(userId);
        if (roleIdsByAccountId.isEmpty()) {
            return false;
        }
        boolean isExist = roleIdsByAccountId.containsAll(tokenUserRoles);

        if (!isExist) {
            return false;
        }
        return true;
    }

    // Kiểm tra token hết hạn
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Lấy thời gian hết hạn từ token
    private Date extractExpiration(String token) {
        try {
            return extractClaim(token, Claims::getExpiration);
        } catch (ExpiredJwtException e) {
            throw new HttpTokenExpiration("Token Expiration");
        }
    }

    // Lấy tất cả claims từ token với parserBuilder
    private Claims extractAllClaims(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("JWT String argument cannot be null or empty");
        }
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}
