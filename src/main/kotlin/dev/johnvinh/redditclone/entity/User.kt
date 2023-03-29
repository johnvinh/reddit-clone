package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "Users")
class User(
    var username: String,
    var password: String,
    @OneToMany var comments: List<Comment> = listOf(),
    @OneToMany var posts: List<Post> = listOf(),
    @OneToMany var upvotedComments: Set<Comment> = setOf(),
    @OneToMany var downvotedComments: Set<Comment> = setOf(),
    @Id @GeneratedValue var id: Long? = null
)