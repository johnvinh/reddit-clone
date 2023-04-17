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
        if (userRepository.findByUsernameIgnoreCase(user.username) == null) {
            // If not, create user
            userRepository.save(user)
        }

    }

    fun login(username: String, password: String): User? {
        val user = userRepository.findByUsernameIgnoreCase(username)
        if (user != null && user.password == password) {
            return user
        }
        return null
    }

    fun usernameIsTaken(username: String): Boolean {
        return userRepository.findByUsernameIgnoreCase(username) != null
    }

    fun getUserByUsername(username: String): User? {
        return userRepository.findByUsernameIgnoreCase(username)
    }
}