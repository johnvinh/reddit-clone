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

/**
 * Represents the data received in a request to create a post.
 *
 * @property title The title of the post.
 * @property textualContent The textual content of the post.
 * @property link The link associated with the post.
 * @property forum The forum in which the post is to be created.
 */
class PostRequest(var title: String, var textualContent: String, var link: String, var forum: String)

/**
 * Handles requests related to posts.
 *
 * @property postService Provides methods related to posts.
 * @property userService Provides methods related to users.
 * @property forumService Provides methods related to forums.
 */
@RestController
@RequestMapping("/api/post")
class PostController(
    private val postService: PostService,
    private val userService: UserService,
    private val forumService: ForumService
) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()

    /**
     * Gets a list of all posts.
     *
     * @return A list of all posts.
     */
    @GetMapping("")
    fun getPosts() = postService.getAllPosts()

    /**
     * Gets a specific post by its ID.
     *
     * @param id The ID of the post.
     * @return The post, or null if no post with the given ID exists.
     */
    @GetMapping("/{id}")
    @ResponseBody
    fun getPostById(@PathVariable id: Long): Post? {
        return postService.getPostById(id)
    }

    /**
     * Creates a new post. The request should include a JWT token for user authentication and a valid link.
     * If the forum with the requested name doesn't exist, the method will return a bad request.
     *
     * @param postRequest The request body, which includes the title, textual content, link, and forum of the post.
     * @param request The HTTP request, from which the JWT token is extracted.
     * @return A ResponseEntity that includes a message and the status of the request.
     */
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
