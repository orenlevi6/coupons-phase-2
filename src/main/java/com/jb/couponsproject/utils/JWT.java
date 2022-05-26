package com.jb.couponsproject.utils;

import com.jb.couponsproject.beans.ClientDetails;
import com.jb.couponsproject.beans.ClientType;
import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.exceptions.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWT {
    private final String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    private final String encodedSecretKey = "coupons+project+encoded+secret+key+oren+lea+tal+omer";
    private final Key decodedSecretKey =
            new SecretKeySpec(Base64.getDecoder().decode(this.encodedSecretKey), this.signatureAlgorithm);

    public String generateToken(ClientDetails clientDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientType", clientDetails.getClientType().toString());
        return createToken(claims, clientDetails.getEmail());
    }

    public String generateToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        Claims myClaims = extractAllClaims(token);
        claims.put("clientType", myClaims.get("clientType"));
        return createToken(claims, myClaims.getSubject());
    }

    private String createToken(Map<String, Object> claims, String email) {
        Instant now = Instant.now();
        return "Bearer " +
                Jwts.builder()
                        .setClaims(claims)
                        .setSubject(email)
                        .setIssuedAt(Date.from(now))
                        .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                        .signWith(this.decodedSecretKey)
                        .compact();
    }

    public Claims extractAllClaims(String token) throws MalformedJwtException, SignatureException, ExpiredJwtException {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(this.decodedSecretKey)
                .build();

        return jwtParser.parseClaimsJws(token.replace("Bearer ", "")).getBody();
    }

    public String extractSignature(String token) throws ExpiredJwtException, MalformedJwtException {
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(decodedSecretKey)
                .build();

        return jwtParser.parseClaimsJws(token).getSignature();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractUserType(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("clientType").toString();
    }

    public Date extractExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenValid(String token) throws MalformedJwtException, SignatureException, ExpiredJwtException {
        final Claims claims = extractAllClaims(token);
        return true;
    }

    public boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException err) {
            return true;
        }
    }

    public boolean validateToken(String token, ClientDetails clientDetails) throws MalformedJwtException, SignatureException, ExpiredJwtException {
        final String userEmail = extractEmail(token);
        return (userEmail.equals(clientDetails.getEmail()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) throws MalformedJwtException, SignatureException {
        final Claims claims = extractAllClaims(token);
        return true;
    }

    public void checkUser(String token, ClientType clientType) throws LoginException, TokenException {
        if (isTokenValid(token)) {
            if (!extractUserType(token).equalsIgnoreCase(clientType.toString())) {
                throw new LoginException(clientType);
            }

        } else {
            throw new TokenException();
        }

    }

}
