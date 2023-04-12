package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Forum(
    var name: String,
    var description: String,
    @OneToOne var owner: User,
    @OneToMany var posts: MutableList<Post> = mutableListOf(),
    @OneToMany var moderators: MutableList<User> = mutableListOf(),
    @Id @GeneratedValue var id: Long? = null,
)