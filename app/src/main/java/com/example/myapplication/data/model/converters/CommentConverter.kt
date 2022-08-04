package com.example.myapplication.data.model.converters

import com.example.myapplication.data.model.CommentEntity
import com.example.myapplication.domain.model.Comment

fun CommentEntity.toComment(): Comment = Comment(
    postId, id, name, email, body
)

fun List<CommentEntity>.toComments() = this.map { it.toComment() }