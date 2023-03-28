package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.ForumRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/forum")
class ForumController(private val repository: ForumRepository)