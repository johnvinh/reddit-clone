package dev.johnvinh.redditclone

import dev.johnvinh.redditclone.entity.User
import dev.johnvinh.redditclone.repository.ForumRepository
import dev.johnvinh.redditclone.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DataInitializer @Autowired constructor(val userRepository: UserRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {
        userRepository.save(User("John"))
    }

}