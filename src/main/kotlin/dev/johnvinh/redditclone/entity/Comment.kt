package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class Comment(
    @OneToOne var author: User,
    var body: String,
    @OneToMany var children: List<Comment> = listOf(),
    var karma: Int = 0,
    @Id @GeneratedValue var id: Long? = null,
)
