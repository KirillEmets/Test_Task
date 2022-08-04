package com.example.myapplication.data.model.converters

import com.example.myapplication.data.model.PostEntity
import com.example.myapplication.domain.model.Post

fun PostEntity.toPost(): Post = Post(
    userId, id, title, body
)

fun List<PostEntity>.toPosts() = this.map { it.toPost() }