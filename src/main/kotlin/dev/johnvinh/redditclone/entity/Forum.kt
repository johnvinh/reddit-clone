package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Forum(
    @Id @GeneratedValue var id: Long? = null,
    var name: String,
    var description: String,
    @OneToOne var owner: User,
    @OneToMany var posts: List<Post>,
    @OneToMany var moderators: List<User>,
)