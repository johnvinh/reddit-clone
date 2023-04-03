package dev.johnvinh.redditclone.controller

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.repository.UserRepository
import dev.johnvinh.redditclone.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController @Autowired constructor(private val service: UserService) {

    @GetMapping("")
    fun get() = service.getAll()

    @PostMapping("/login")
    fun login(@RequestParam("username") username: String, @RequestParam("password") password: String) {

    }

    @PostMapping("/register")
    fun register(@RequestParam("username") username: String, @RequestParam("password") password: String) {
        val user = User(username, password)
        service.register(user)
    }
}