package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "Users")
class User(
    @Id @GeneratedValue var id: Long? = null,
    @OneToMany var comments: List<Comment>,
    @OneToMany var posts: List<Post>
)