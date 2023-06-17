package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

/**
 * Service class for managing posts.
 *
 * @property repository Autowired instance of the [PostRepository].
 * @constructor creates a new instance of [PostService].
 *
 * @param repository the repository to manage posts.
 */
@Service
class PostService @Autowired constructor(private val repository: PostRepository) {

    /**
     * Fetches all posts from the database.
     *
     * @return an Iterable of [Post] objects.
     */
    fun getAllPosts(): Iterable<Post> = repository.findAll()

    /**
     * Fetches a single post by its ID.
     *
     * @param id the ID of the post.
     * @return the post with the specified ID, or null if no post with such ID exists.
     */
    fun getPostById(id: Long): Post? = repository.findByIdOrNull(id)

    /**
     * Creates a new post.
     *
     * @param post the post to create.
     * @return the created post.
     */
    fun createPost(post: Post): Post {
        return repository.save(post)
    }
}