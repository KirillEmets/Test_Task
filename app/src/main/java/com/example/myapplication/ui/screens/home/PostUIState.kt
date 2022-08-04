package com.example.myapplication.ui.screens.home

class PostUIState(
    val postId: Int,
    val userId: Int,
    val userName: String?,
    val title: String,
    val body: String,
    val comments: List<CommentUIState>?
)