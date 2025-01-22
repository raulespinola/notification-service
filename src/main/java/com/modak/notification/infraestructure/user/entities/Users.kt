package com.modak.notification.infraestructure.user.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class Users (
    @Id val id: Long?,
    val name:String
    )