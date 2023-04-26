package com.team033.teamup.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Date;

public class JwtUtil {
    private final static String secretKey = "Team-33_DD";

    // Expiration time of JWT
    private final static Duration expiration = Duration.ofHours(2);

    /**
     * Generate JWT
     * @param userName
     * @return JWT
     */
    public static String generate(String userName) {

        Date expiryDate = new Date(System.currentTimeMillis() + expiration.toMillis());

        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * Parse JWT
     * @param token JWT
     * @return Claims on success; null on failure
     */
    public static Claims parse(String token) {
        // check if token is empty
        if (!StringUtils.hasLength(token)) {
            return null;
        }

        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            System.err.println("JWT parsing failed！");
        }
        return claims;
    }

}
