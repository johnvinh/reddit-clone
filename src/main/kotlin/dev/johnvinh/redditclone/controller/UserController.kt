package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository)