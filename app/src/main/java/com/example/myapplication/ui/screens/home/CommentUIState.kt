package com.example.myapplication.ui.screens.home

import com.example.myapplication.domain.model.Comment

class CommentUIState(
    val name: String,
    val email: String,
    val body: String
)

fun Comment.toCommentUIState() = CommentUIState(name, email, body)