package com.proyecto.demos.Service.Jwt;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JwtService {
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String createToken(UserDetails userDetails){
        Algorithm algorithm=Algorithm.HMAC256(privateKey);
        String autoridades=userDetails.getAuthorities().stream()
            .map(e->e.getAuthority()) //transforma en cadena
            .collect(Collectors.joining(","));
        //
        String jwtToken=JWT.create()
            .withIssuer(userGenerator)
            .withSubject(userDetails.getUsername())
            .withClaim("authorithies", autoridades)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
            .withNotBefore(new Date(System.currentTimeMillis()))
            .sign(algorithm);
        return jwtToken;
    }
    public boolean isTokenValid(String token, UserDetails userDetails){
        String username=extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    public String extractUsername(String token){
        return JWT.decode(token).getSubject();
    }
    public boolean isTokenExpired(String token){
        Date expiration=JWT.decode(token).getExpiresAt();
        return expiration.before(new Date());
    }
}
