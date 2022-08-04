package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Comment
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.User


interface ForumRepository {
    suspend fun getPosts(): List<Post>
    suspend fun getCommentsForPost(postId: Int): List<Comment>
    suspend fun getUser(userId: Int): User
}