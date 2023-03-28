package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("")
    fun get(): Iterable<User> {
        return repository.findAll()
    }
}