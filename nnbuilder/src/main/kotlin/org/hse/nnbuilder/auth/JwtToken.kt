package org.hse.nnbuilder.auth

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.apache.commons.lang3.time.DateUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.stereotype.Component
import java.security.SignatureException
import java.util.Date

@Component
class JwtToken {
    @Value("\${JWT_SIGNING_KEY}")
    lateinit var jwtSigningKey: String

    fun generateJwtToken(authentication: Authentication): String {
        val userPrincipal = authentication.principal as UserDetails
        val now = Date()

        return Jwts.builder()
            .setSubject(userPrincipal.username)
            .claim("authorities", authentication.authorities.joinToString(",") { it.authority })
            .setIssuedAt(now)
            .setExpiration(DateUtils.addDays(now, 1))
            .signWith(SignatureAlgorithm.HS512, jwtSigningKey)
            .compact()
    }

    //TODO обработать ошибки
    fun parseJwtToken(jwt: String): Authentication? {
        val claims = try {
            Jwts.parser().setSigningKey(jwtSigningKey).parseClaimsJws(jwt).body
        } catch (e: SignatureException) {
            return null
        }

        if (claims.expiration.before(Date())) {
            return null
        }

        val authoritiesString = claims.get("authorities", String::class.java) ?: return null
        val userDetails = User.builder()
            .username(claims.subject)
            .password("")
            .authorities(*authoritiesString.split(",").toTypedArray())
            .build()
        return PreAuthenticatedAuthenticationToken(userDetails, jwt, userDetails.authorities)
    }

}
