package dev.johnvinh.redditclone.repository

import dev.johnvinh.redditclone.entity.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
    fun findByUsername(username: String): User?
    fun findByUsernameIgnoreCase(username: String): User?
}