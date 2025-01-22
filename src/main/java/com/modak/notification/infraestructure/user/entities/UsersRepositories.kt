package com.modak.notification.infraestructure.user.entities

import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:R2dbcRepository<Users, Long>