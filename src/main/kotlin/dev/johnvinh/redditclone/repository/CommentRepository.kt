package dev.johnvinh.redditclone.repository

import dev.johnvinh.redditclone.entity.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long>