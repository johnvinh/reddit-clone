package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Forum
import dev.johnvinh.redditclone.repository.ForumRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ForumService @Autowired constructor(private val repository: ForumRepository) {
    fun getAllForums(): Iterable<Forum> = repository.findAll()
    fun createForum(forum: Forum) {
        repository.save(forum)
    }

    fun getForumByName(name: String): Forum? {
        return repository.findForumByNameIgnoreCase(name)
    }
}