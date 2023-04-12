package dev.johnvinh.redditclone.repository

import dev.johnvinh.redditclone.entity.Forum
import org.springframework.data.repository.CrudRepository

interface ForumRepository : CrudRepository<Forum, Long> {
    fun findForumByNameIgnoreCase(name: String): Forum?
}