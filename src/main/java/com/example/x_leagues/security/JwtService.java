package com.example.x_leagues.security;

import com.example.x_leagues.model.AppUser;
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
import java.util.UUID;
import java.util.function.Function;


@Service
public class JwtService {

    private static final String SECRET_KEY = "4HmrkFLzXppXXdHbJPbkTwcD9ET6DJy6rLxDZJjArDj9BYCuYNR6Zm5SBWtwUds2LYCP7zSNxyT2XkH6aBKPP6eQFrb2qUb5MGkNPdmUAhdNFTqh397ax4ADkA46Xs8cpdcUXS37PCuHKtk4zCZe9oLSHBAp3fwyezua98CAZs4U6CzFMbnLbkbuHxTrkksAFdwrA3QFMwpSuSrzmbJDKe5d8Qu7UUH5ySxfgHGYYJE8HmnSgux6zksUT44ocoFM";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public UUID extractUserId(String token) {
        return UUID.fromString(extractClaim(token, claims -> claims.get("id", String.class)));
    }

    public String extractUserRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public String generateToken(
        Map<String, Object> claims,
        UserDetails userDetails
    ) {
        UUID userId = ((AppUser) userDetails).getId();

        String role = userDetails.getAuthorities().stream()
            .map(Object::toString)
            .filter(authority -> authority.startsWith("ROLE_"))
            .findFirst()
            .orElse("");

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .claim("id", userId.toString())
            .claim("role", role)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwt) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(jwt)
            .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
