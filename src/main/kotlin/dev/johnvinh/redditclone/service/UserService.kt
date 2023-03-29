package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {

    fun getAll(): Iterable<User> = userRepository.findAll()
}