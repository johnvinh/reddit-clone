package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.PostRepository
import dev.johnvinh.redditclone.service.PostService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/post")
class PostController(private val service: PostService) {

}