package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Forum
import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.repository.ForumRepository
import jakarta.persistence.EntityManagerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumService @Autowired constructor(private val repository: ForumRepository, private val entityManagerFactory: EntityManagerFactory) {
    fun getAllForums(): Iterable<Forum> = repository.findAll()
    fun createForum(forum: Forum) {
        repository.save(forum)
    }

    fun getForumByName(name: String): Forum? {
        return repository.findForumByNameIgnoreCase(name)
    }

    fun addPost(forum: Forum, post: Post) {
        val entityManager = entityManagerFactory.createEntityManager()
        entityManager.detach(forum)
        forum.posts.add(post)
        repository.save(forum)
        entityManager.close()
    }
}