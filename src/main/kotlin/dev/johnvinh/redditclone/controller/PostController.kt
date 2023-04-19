package dev.johnvinh.redditclone.controller

import JwtKey
import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.service.ForumService
import dev.johnvinh.redditclone.service.PostService
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

class PostRequest(var title: String, var textualContent: String, var link: String, var forum: String)

@RestController
@RequestMapping("/api/post")
class PostController(
    private val postService: PostService,
    private val userService: UserService,
    private val forumService: ForumService
) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()

    @GetMapping("")
    fun getPosts() = postService.getAllPosts()

    @GetMapping("/{id}")
    @ResponseBody
    fun getPostById(@PathVariable id: Long): Post? {
        return postService.getPostById(id)
    }

    @PostMapping("")
    fun createPost(@RequestBody postRequest: PostRequest, request: HttpServletRequest): ResponseEntity<*> {
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
        val forum = forumService.getForumByName(postRequest.forum) ?: return ResponseEntity.badRequest().body(mapOf("message" to "Invalid forum"))
        // Check that postRequest.link is a link using regex
        val linkRegex = Regex("^(http|https)://.*")
        if (postRequest.link.isNotEmpty() && !linkRegex.matches(postRequest.link)) {
            return ResponseEntity.badRequest().body(mapOf("message" to "Invalid link"))
        }
        val post = Post(
            title = postRequest.title,
            textualContent = postRequest.textualContent,
            link = postRequest.link,
            author = user,
            0,
            listOf(),
            forum
        )
        val newPost = postService.createPost(post)
        forumService.addPost(forum, post)
        return ResponseEntity.ok(mapOf("message" to "Post created", "id" to newPost.id, "forum" to newPost.forum.name))
    }
}