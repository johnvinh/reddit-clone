package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.CommentRepository
import dev.johnvinh.redditclone.repository.ForumRepository
import dev.johnvinh.redditclone.repository.PostRepository
import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository)

@RestController
@RequestMapping("/api/comment")
class CommentController(private val repository: CommentRepository)

@RestController
@RequestMapping("/api/post")
class PostController(private val repository: PostRepository)

@RestController
@RequestMapping("/api/forum")
class ForumController(private val repository: ForumRepository)