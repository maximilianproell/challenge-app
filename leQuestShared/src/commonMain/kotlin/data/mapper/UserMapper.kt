package data.mapper

import data.local.entity.UserEntity
import domain.model.User

fun UserEntity.toDomain(): User {
    return User(
        name = name,
        xp = xp,
    )
}