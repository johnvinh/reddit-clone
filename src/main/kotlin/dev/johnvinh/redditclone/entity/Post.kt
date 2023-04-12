package dev.johnvinh.redditclone.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Post(
    var title: String,
    var textualContent: String?,
    var link: String?,
    @ManyToOne var author: User,
    var upvotes: Int = 0,
    @OneToMany var comments: List<Comment>,
    @JsonIgnoreProperties("posts") @ManyToOne var forum: Forum,
    @Id @GeneratedValue var id: Long? = null
)