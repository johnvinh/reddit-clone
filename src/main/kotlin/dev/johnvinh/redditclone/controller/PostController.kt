package dev.johnvinh.redditclone.controller

import JwtKey
import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.getUserFromJwt
import dev.johnvinh.redditclone.service.ForumService
import dev.johnvinh.redditclone.service.PostService
import dev.johnvinh.redditclone.service.UserService
import dev.johnvinh.redditclone.verifyJwtToken
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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
        val userResponse = getUserFromJwt(parser, request, userService)
        if (!userResponse.statusCode.is2xxSuccessful) {
            return userResponse
        }

        val user = userResponse.body as User
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