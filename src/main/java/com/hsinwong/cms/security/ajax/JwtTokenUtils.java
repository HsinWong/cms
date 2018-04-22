package com.hsinwong.cms.security.ajax;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

public final class JwtTokenUtils {

    private static final String SECRET = UUID.randomUUID().toString();

    public static String createToken(Long userId) {
        try {
            String token = JWT.create()
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

        throw new UnsupportedOperationException("token生成失败");
    }

    public static Optional<Long> verifyToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
            return Optional.of(decodedJWT.getClaim("userId").asLong());
        } catch (UnsupportedEncodingException | JWTVerificationException e) {
            return Optional.empty();
        }
    }
}