package com.querybridge.api_gateway.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

	private static final String SECRET =
	        "QueryBridgeSecretKeyQueryBridgeSecretKeyQueryBridgeSecretKey";

	    public static String extractEmail(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(SECRET.getBytes())
	                .parseClaimsJws(token)
	                .getBody();
	        return claims.getSubject();
	    }

	    public static String extractRole(String token) {
	        Claims claims = Jwts.parser()
	                .setSigningKey(SECRET.getBytes())
	                .parseClaimsJws(token)
	                .getBody();
	        return claims.get("role", String.class);
	    }

		public static String generateToken(String email, String role) {
			return Jwts.builder().setSubject(email).claim("role", role).setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
					.signWith(SignatureAlgorithm.HS256, SECRET.getBytes()).compact();
		}

}
