package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Forum
import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.repository.ForumRepository
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Provides services related to forums.
 *
 * @property repository The forum repository.
 * @property entityManagerFactory The entity manager factory.
 */
@Service
class ForumService @Autowired constructor(private val repository: ForumRepository, private val entityManagerFactory: EntityManagerFactory) {

    /**
     * Retrieves all forums.
     *
     * @return An Iterable of all forums.
     */
    fun getAllForums(): Iterable<Forum> = repository.findAll()

    /**
     * Creates a new forum.
     *
     * @param forum The forum to be created.
     */
    fun createForum(forum: Forum) {
        repository.save(forum)
    }

    /**
     * Retrieves a forum by its name. The search is case-insensitive.
     *
     * @param name The name of the forum.
     * @return The forum, or null if no forum with the given name exists.
     */
    fun getForumByName(name: String): Forum? {
        return repository.findForumByNameIgnoreCase(name)
    }

    /**
     * Adds a post to a forum.
     *
     * @param forum The forum to which the post will be added.
     * @param post The post to be added.
     */
    fun addPost(forum: Forum, post: Post) {
        val entityManager = entityManagerFactory.createEntityManager()
        entityManager.detach(forum)
        forum.posts.add(post)
        repository.save(forum)
        entityManager.close()
    }
}
