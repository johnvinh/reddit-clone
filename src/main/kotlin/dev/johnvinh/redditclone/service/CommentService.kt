package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Comment
import dev.johnvinh.redditclone.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommentService @Autowired constructor(private val repository: CommentRepository) {
    fun createComment(comment: Comment) {
        repository.save(comment)
    }
}
