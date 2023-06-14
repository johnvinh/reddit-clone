package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.entity.Comment
import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.getUserFromJwt
import dev.johnvinh.redditclone.service.CommentService
import dev.johnvinh.redditclone.service.PostService
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class CommentRequest(var content: String, var postId: Long)

@RestController
@RequestMapping("/api/comment")
class CommentController(private val commentService: CommentService, private val userService: UserService, private val postService: PostService) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()
    @PostMapping("/create")
    fun createComment(@RequestBody commentRequest: CommentRequest, request: HttpServletRequest): ResponseEntity<*> {
        val userResponse = getUserFromJwt(parser, request, userService)
        if (!userResponse.statusCode.is2xxSuccessful) {
            return userResponse
        }

        val user = userResponse.body as User
        val comment = Comment(user, commentRequest.content)
        val post = postService.getPostById(commentRequest.postId) ?: return ResponseEntity.badRequest().body("Post not found")
        post.comments = post.comments.plus(comment)
        postService.createPost(post)
        return ResponseEntity.ok(commentService.createComment(comment))
    }
}
