package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Comment
import dev.johnvinh.redditclone.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Service class for managing comments.
 *
 * @property repository Autowired instance of the [CommentRepository].
 * @constructor Creates a new instance of [CommentService].
 *
 * @param repository The repository to manage comments.
 */
@Service
class CommentService @Autowired constructor(private val repository: CommentRepository) {
    /**
     * Creates a new comment.
     *
     * @param comment The comment to be created.
     */
    fun createComment(comment: Comment) {
        repository.save(comment)
    }
}
