package com.example.MedicExpress.Utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;

public class JwtUtils {
    @Value("${security.jwt.secret-key}")
    private String secretKey;

    @Value("${security.jwt.expiration-time}")
    private long jwtExpiration;



    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_00)) // 1 jour
                .signWith(getSigningKey())
                .compact();
    }

//    public String extractEmail(String token) {
//        return Jwts.parser().setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody().getSubject();
//    }

//    public boolean isTokenValid(String token, String username) {
//        return extractEmail(token).equals(username);
//    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
