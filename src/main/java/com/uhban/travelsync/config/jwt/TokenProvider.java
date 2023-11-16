package com.uhban.travelsync.config.jwt;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.type.TokenType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.time.ZonedDateTime;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.accessToken.expiration}")
    private Long accessTokenExpiration;

    @Value("${app.jwt.refreshToken.expiration}")
    private Long refreshTokenExpiration;


    //Access 토큰 생성
    public String generateAccessToken(PrincipalDetails principalDetails) {
        byte[] signingKey = jwtSecret.getBytes(Charset.forName("UTF-8"));

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(accessTokenExpiration).toInstant()))
                .setSubject(principalDetails.getUsername())
                .claim("userId", principalDetails.getUser().getUserId())
                .claim("type", TokenType.ACCESS)
                .compact();
    }

    //Refresh 토큰 생성
    public String generateRefreshToken(PrincipalDetails principalDetails) {
        byte[] signingKey = jwtSecret.getBytes(Charset.forName("UTF-8"));

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
                .setExpiration(Date.from(ZonedDateTime.now().plusDays(refreshTokenExpiration).toInstant()))
                .setSubject(principalDetails.getUsername())
                .claim("userId", principalDetails.getUser().getUserId())
                .claim("type", TokenType.REFRESH)
                .compact();
    }
    //토큰에서 username 추출
    public String getUserIdByToken(String token) {
        byte[] signingKey = jwtSecret.getBytes(Charset.forName("UTF-8"));

        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    //토큰 타입 반환
    public String getTokenTypeByToken(String token) {
        byte[] signingKey = jwtSecret.getBytes(Charset.forName("UTF-8"));

        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("type")
                .toString();
    }
    //token 유효성 검사
    public boolean validateToken(String token) {
        log.info("===== JWT Token validating - TokenProvider ======");
        byte[] signingKey = jwtSecret.getBytes(Charset.forName("UTF-8"));
        Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token);
        return true;
    }
}
