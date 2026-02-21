package com.cdacQuerybridge.admin_service.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtUtil {

    private static final String SECRET = "QueryBridgeSecretKeyQueryBridgeSecretKeyQueryBridgeSecretKey";

    public static String extractRole(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.get("role", String.class);
    }

    public static void validateAdmin(String token) {
        if (!"ADMIN".equals(extractRole(token))) {
            throw new RuntimeException("Access denied: Admin only");
        }
    }
}
