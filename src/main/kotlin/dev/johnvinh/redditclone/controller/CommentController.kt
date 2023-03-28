package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.CommentRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/comment")
class CommentController(private val repository: CommentRepository)