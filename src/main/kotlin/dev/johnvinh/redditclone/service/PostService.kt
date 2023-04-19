package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.Post
import dev.johnvinh.redditclone.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class PostService @Autowired constructor(private val repository: PostRepository) {

    fun getAllPosts(): Iterable<Post> = repository.findAll()

    fun getPostById(id: Long): Post? = repository.findByIdOrNull(id)

    fun createPost(post: Post): Post {
        return repository.save(post)
    }
}