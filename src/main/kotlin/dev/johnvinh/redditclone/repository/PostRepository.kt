package dev.johnvinh.redditclone.repository

import dev.johnvinh.redditclone.entity.Post
import org.springframework.data.repository.CrudRepository

interface PostRepository : CrudRepository<Post, Long>