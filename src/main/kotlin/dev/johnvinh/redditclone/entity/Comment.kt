package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Comment(
    @Id @GeneratedValue var id: Long? = null,
    var author: User,
    var body: String,
    @OneToMany var children: List<Comment>
)