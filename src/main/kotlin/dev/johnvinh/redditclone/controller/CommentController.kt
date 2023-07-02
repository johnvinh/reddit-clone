package dev.johnvinh.redditclone.controller

import JwtKey
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

/**
 * The class representing a request to create a comment.
 * @property content the content of the comment to be created.
 * @property postId the ID of the post where the comment should be added.
 */
class CommentRequest(var content: String, var postId: Long)

/**
 * The REST controller for handling requests related to comments.
 * @property commentService the service class for handling business logic related to comments.
 * @property userService the service class for handling business logic related to users.
 * @property postService the service class for handling business logic related to posts.
 */
@RestController
@RequestMapping("/api/comment")
class CommentController(
    private val commentService: CommentService,
    private val userService: UserService,
    private val postService: PostService
) {
    /**
     * The parser for handling JWTs.
     */
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()

    /**
     * The endpoint for creating a new comment.
     * @param commentRequest the request object containing the content of the comment and the post ID.
     * @param request the HTTP request object. This is used for parsing the JWT of the user who created the comment.
     * @return a ResponseEntity that contains the comment if successful, or an error message otherwise.
     */
    @PostMapping("/create")
    fun createComment(@RequestBody commentRequest: CommentRequest, request: HttpServletRequest): ResponseEntity<*> {
        // Gets the user who made the request using their JWT.
        val userResponse = getUserFromJwt(parser, request, userService)
        // If the user retrieval was not successful, return the error response.
        if (!userResponse.statusCode.is2xxSuccessful) {
            return userResponse
        }

        // Create the comment and add it to the post.
        val user = userResponse.body as User
        val comment = Comment(user, commentRequest.content)
        commentService.createComment(comment)
        
        val post =
            postService.getPostById(commentRequest.postId) ?: return ResponseEntity.badRequest().body("Post not found")
        post.comments = post.comments.plus(comment)

        // Save the post and the comment, then return the created comment.
        postService.createPost(post)
        return ResponseEntity.ok(commentService.createComment(comment))
    }
}
