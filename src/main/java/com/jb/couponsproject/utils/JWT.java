package com.jb.couponsproject.utils;

import com.jb.couponsproject.exceptions.LoginException;
import com.jb.couponsproject.exceptions.TokenException;
import com.jb.couponsproject.login.ClientDetails;
import com.jb.couponsproject.login.ClientType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWT {
    private final String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    private final String encodedSecretKey = "coupons+project+encoded+secret+key+example+oren";
    private final Key decodedSecretKey = new SecretKeySpec(Base64.getDecoder().decode(this.encodedSecretKey), this.signatureAlgorithm);

    public String generateToken(ClientDetails clientDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("clientType", clientDetails.getClientType().toString());
        return createToken(claims, clientDetails.getEmail());
    }

    public String generateToken(String token) {
        Map<String, Object> claims = new HashMap<>();
        Claims myClaims = extractAllClaims(token.replace("Bearer ", ""));
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

        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getSubject();
    }

    public Date extractExpirationDate(String token) {
        return extractAllClaims(token.replace("Bearer ", "")).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        try {
            extractAllClaims(token.replace("Bearer ", ""));
            return false;
        } catch (ExpiredJwtException err) {
            return true;
        }
    }

    public String getClientType(String token) {
        Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        return claims.get("clientType").toString();
    }

    public boolean validateToken(String token, ClientDetails clientDetails) throws MalformedJwtException, SignatureException, ExpiredJwtException {
        final String userEmail = extractEmail(token.replace("Bearer ", ""));
        return (userEmail.equals(clientDetails.getEmail()) && !isTokenExpired(token));
    }

    public boolean validateToken(String token) throws MalformedJwtException, SignatureException {
        final Claims claims = extractAllClaims(token.replace("Bearer ", ""));
        return true;
    }

    public void checkUser(String token, ClientType clientType) throws LoginException, TokenException {
        String newToken = token.replace("Bearer ", "");
        try {
            if (validateToken(newToken)) {
                if (!getClientType(newToken).equals(clientType.toString())) {
                    throw new LoginException("User not allowed");
                }
            }
        } catch (MalformedJwtException | SignatureException | ExpiredJwtException err) {
            throw new TokenException("Invalid Token");
        }
    }

}
