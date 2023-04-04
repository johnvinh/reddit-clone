package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService @Autowired constructor(private val repository: PostRepository) {
    fun createPost(post: Post) {
        repository.save(post)
    }
}