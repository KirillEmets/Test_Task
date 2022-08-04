package com.example.myapplication.data.model.converters

import com.example.myapplication.data.model.UserEntity
import com.example.myapplication.domain.model.User

fun UserEntity.toUser(): User = User(
    id, name, username, email
)

fun List<UserEntity>.toUsers() = this.map { it.toUser() }