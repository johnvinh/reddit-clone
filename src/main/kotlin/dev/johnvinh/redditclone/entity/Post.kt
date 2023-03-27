package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Post(
    @Id @GeneratedValue var id: Long? = null,
    var title: String,
    var textualContent: String?,
    var link: String?,
    @OneToMany var comments: List<Comment>
)