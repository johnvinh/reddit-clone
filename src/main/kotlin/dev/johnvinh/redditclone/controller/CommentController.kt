package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.CommentRepository
import io.jsonwebtoken.Jwts
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class CommentRequest(var content: String)

@RestController
@RequestMapping("/api/comment")
class CommentController(private val repository: CommentRepository) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()
    @PostMapping("/create")
    fun createComment(@RequestBody commentRequest: CommentRequest) {

    }
}
