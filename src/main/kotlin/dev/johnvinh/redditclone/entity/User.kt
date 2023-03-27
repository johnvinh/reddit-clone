package dev.johnvinh.redditclone.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class User(
    @Id @GeneratedValue var id: Long? = null,
)