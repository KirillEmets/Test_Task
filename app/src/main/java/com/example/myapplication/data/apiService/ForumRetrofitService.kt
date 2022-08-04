package com.example.myapplication.data.apiService

import com.example.myapplication.data.model.CommentEntity
import com.example.myapplication.data.model.PostEntity
import com.example.myapplication.data.model.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ForumRetrofitService {
    @GET("/posts")
    suspend fun getPosts(): List<PostEntity>

    @GET("/comments")
    suspend fun getCommentsForPost(@Query("postId") postId: Int): List<CommentEntity>

    @GET("/users/{id}")
    suspend fun getUser(@Path("id") userId: Int): UserEntity
}


