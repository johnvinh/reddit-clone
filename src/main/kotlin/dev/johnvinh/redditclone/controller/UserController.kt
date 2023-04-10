package dev.johnvinh.redditclone.controller

import JwtKey
import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

class RegistrationRequest(val username: String, val password: String)

@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(private val service: UserService) {

    @GetMapping("")
    fun get() = service.getAll()

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: RegistrationRequest): ResponseEntity<*> {
        val user = service.login(loginRequest.username, loginRequest.password)
            ?: return ResponseEntity.badRequest().body(mapOf("message" to "Invalid credentials"))
        val key = JwtKey.secretKey
        val jwt = Jwts.builder()
            .setSubject(user.username)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return ResponseEntity.ok(mapOf("token" to jwt, "username" to user.username))
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegistrationRequest): ResponseEntity<*> {
        if (service.usernameIsTaken(registerRequest.username)) {
            return ResponseEntity.badRequest().body(mapOf("message" to "Username is taken"))
        }
        val newUser = User(registerRequest.username, registerRequest.password)
        service.register(newUser)
        val key = JwtKey.secretKey
        val jwt = Jwts.builder()
            .setSubject(newUser.username)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return ResponseEntity.ok(mapOf("token" to jwt))
    }
}