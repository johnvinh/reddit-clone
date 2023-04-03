package dev.johnvinh.redditclone.service

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(private val userRepository: UserRepository) {

    fun getAll(): Iterable<User> = userRepository.findAll()

    fun register(user: User) {
        // Check if user exists
        if (userRepository.findByUsername(user.username) == null) {
            // If not, create user
            userRepository.save(user)
        }

    }

    fun usernameIsTaken(username: String): Boolean {
        return userRepository.findByUsername(username) != null
    }
}