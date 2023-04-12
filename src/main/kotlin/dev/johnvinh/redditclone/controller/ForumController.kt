package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.entity.Forum
import dev.johnvinh.redditclone.repository.ForumRepository
import dev.johnvinh.redditclone.service.ForumService
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

class ForumRequest(var name: String, var description: String)

@RestController
@RequestMapping("/api/forum")
class ForumController(private val userService: UserService, private val forumService: ForumService) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()

    @GetMapping("")
    fun getAllForums() = forumService.getAllForums()

    @GetMapping("/{forum}")
    @ResponseBody
    fun getSpecificForum(@PathVariable("forum") forum: String): Forum? {
        // Casing doesn't matter when requesting a forum
        return forumService.getForumByName(forum)
    }

    @PostMapping("/create")
    fun createForum(@RequestBody forumRequest: ForumRequest, request: HttpServletRequest): ResponseEntity<*> {
        val token = request.getHeader("Authorization")?.replace("Bearer ", "")
        println("Token: $token")
        val claims: Claims = try {
            parser.parseClaimsJws(token).body
        } catch (e: io.jsonwebtoken.security.SignatureException) {
            return ResponseEntity.badRequest().body(mapOf("message" to "Invalid token"))
        }
        val username = claims.subject
        val user = userService.getUserByUsername(username) ?: return ResponseEntity.badRequest()
            .body(mapOf("message" to "Invalid token"))
        if (forumService.getForumByName(forumRequest.name) != null) {
            return ResponseEntity.badRequest().body(mapOf("message" to "Taken"))
        }
        val forum = Forum(
            forumRequest.name,
            forumRequest.description,
            user
        )
        forumService.createForum(forum)
        return ResponseEntity.ok(mapOf("message" to "Forum created"))
    }
}