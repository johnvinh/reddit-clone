package dev.johnvinh.redditclone.controller

import JwtKey
import dev.johnvinh.redditclone.entity.Forum
import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.getUserFromJwt
import dev.johnvinh.redditclone.service.ForumService
import dev.johnvinh.redditclone.service.UserService
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Represents the data received in a request to create a forum.
 *
 * @property name The name of the forum.
 * @property description The description of the forum.
 */
class ForumRequest(var name: String, var description: String)

/**
 * Handles requests related to forums.
 *
 * @property userService Provides methods related to users.
 * @property forumService Provides methods related to forums.
 */
@RestController
@RequestMapping("/api/forum")
class ForumController(private val userService: UserService, private val forumService: ForumService) {
    private val parser = Jwts.parserBuilder().setSigningKey(JwtKey.secretKey).build()

    /**
     * Gets a list of all forums.
     *
     * @return A list of all forums.
     */
    @GetMapping("")
    fun getAllForums() = forumService.getAllForums()

    /**
     * Gets a specific forum by its name. The case of the forum name does not matter.
     *
     * @param forum The name of the forum.
     * @return The forum, or null if no forum with the given name exists.
     */
    @GetMapping("/{forum}")
    @ResponseBody
    fun getSpecificForum(@PathVariable("forum") forum: String): Forum? {
        // Casing doesn't matter when requesting a forum
        return forumService.getForumByName(forum)
    }

    /**
     * Creates a new forum. The request should include a JWT token for user authentication.
     * If a forum with the requested name already exists, the method will return a bad request.
     *
     * @param forumRequest The request body, which includes the name and description of the forum.
     * @param request The HTTP request, from which the JWT token is extracted.
     * @return A ResponseEntity that includes a message and the status of the request.
     */
    @PostMapping("/create")
    fun createForum(@RequestBody forumRequest: ForumRequest, request: HttpServletRequest): ResponseEntity<*> {
        val userResponse = getUserFromJwt(parser, request, userService)
        if (!userResponse.statusCode.is2xxSuccessful) {
            return userResponse
        }

        val user = userResponse.body as User
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
