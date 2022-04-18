package org.hse.nnbuilder.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWT {
    public static String getJwt(Long userId) {
        return Jwts.builder()
                .setSubject(userId.toString()) // client's identifier
                .signWith(SignatureAlgorithm.HS256, Constants.JWT_SIGNING_KEY)
                .compact();
    }
}
