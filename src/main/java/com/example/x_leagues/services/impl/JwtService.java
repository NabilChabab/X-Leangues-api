package com.example.x_leagues.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "4HmrkFLzXppXXdHbJPbkTwcD9ET6DJy6rLxDZJjArDj9BYCuYNR6Zm5SBWtwUds2LYCP7zSNxyT2XkH6aBKPP6eQFrb2qUb5MGkNPdmUAhdNFTqh397ax4ADkA46Xs8cpdcUXS37PCuHKtk4zCZe9oLSHBAp3fwyezua98CAZs4U6CzFMbnLbkbuHxTrkksAFdwrA3QFMwpSuSrzmbJDKe5d8Qu7UUH5ySxfgHGYYJE8HmnSgux6zksUT44ocoFM";

    public String extractUserName(String jwt) {
        return extractClaim(jwt, Claims::getSubject);
    }


    public <T> T extractClaim(String jwt, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwt);
        return claimsResolver.apply(claims);
    }


    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>() , userDetails);
    }


    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSigningKey() , SignatureAlgorithm.HS256)
            .compact();
    }


    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUserName(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);
    }

    public boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    public Date extractExpiration(String jwt) {
        return extractClaim(jwt, Claims::getExpiration);
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(jwt)
            .getBody();
    }

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JwtService.SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
