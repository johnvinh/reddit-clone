package dev.johnvinh.redditclone

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import java.util.*

/**
 * Verifies a JWT token by parsing the token from the request and checking if a user exists with the username in the token's claims.
 *
 * @param parser The JWT parser to parse the token.
 * @param request The HTTP request from which to extract the Authorization header containing the JWT token.
 * @param userService The user service to find the user by username.
 * @return Optional of User. If the user exists, it is wrapped in the Optional. If not, an empty Optional is returned.
 */
fun verifyJwtToken(parser: JwtParser, request: HttpServletRequest, userService: UserService): Optional<User> {
    val token = request.getHeader("Authorization")?.replace("Bearer ", "")
    val claims: Claims = parser.parseClaimsJws(token).body
    val username = claims.subject
    val user = userService.getUserByUsername(username) ?: return Optional.empty()
    return Optional.of(user)
}

/**
 * Extracts the user from the JWT token present in the HTTP request.
 * If the token is invalid or the user does not exist, it returns a bad request response.
 *
 * @param parser The JWT parser to parse the token.
 * @param request The HTTP request from which to extract the Authorization header containing the JWT token.
 * @param userService The user service to find the user by username.
 * @return ResponseEntity of User if the token is valid and the user exists, else ResponseEntity with an error message.
 */
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
