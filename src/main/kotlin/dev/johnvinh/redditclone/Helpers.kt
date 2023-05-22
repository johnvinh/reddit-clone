package dev.johnvinh.redditclone

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import java.util.*

fun verifyJwtToken(parser: JwtParser, request: HttpServletRequest, userService: UserService): Optional<User> {
    val token = request.getHeader("Authorization")?.replace("Bearer ", "")
    println("Token: $token")
    val claims: Claims = parser.parseClaimsJws(token).body
    val username = claims.subject
    val user = userService.getUserByUsername(username) ?: return Optional.empty()
    return Optional.of(user)
}

fun getUserFromJwt(parser: JwtParser, request: HttpServletRequest, userService: UserService): ResponseEntity<*> {
    return try {
        val user = verifyJwtToken(parser, request, userService).orElse(null)
        if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.badRequest().body(mapOf("message" to "Invalid token"))
        }
    } catch (e: io.jsonwebtoken.security.SignatureException) {
        ResponseEntity.badRequest().body(mapOf("message" to "Invalid token"))
    }
}
