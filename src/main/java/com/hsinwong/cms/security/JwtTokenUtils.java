package com.hsinwong.cms.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public final class JwtTokenUtils {

    private static final String SECRET = UUID.randomUUID().toString();

    public static String createToken(Long userId) {
        String token = null;

        try {
            token = JWT.create()
                    .withClaim("userId", userId)
                    .withIssuedAt(Date.from(Instant.now()))
                    .withExpiresAt(Date.from(Instant.now().plus(8, ChronoUnit.HOURS)))
                    .sign(Algorithm.HMAC256(SECRET));
            return token;
        } catch (UnsupportedEncodingException exception) {
            //UTF-8 encoding not supported
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }

        Assert.hasText(token, "token生成失败");
        return token;
    }

    public static Long verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return decodedJWT.getClaim("userId").asLong();
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            return null;
        }
    }
}