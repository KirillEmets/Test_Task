package com.example.myapplication.data.repository

import com.example.myapplication.data.apiService.ForumRetrofitService
import com.example.myapplication.data.model.converters.toComments
import com.example.myapplication.data.model.converters.toPosts
import com.example.myapplication.data.model.converters.toUser
import com.example.myapplication.domain.model.Comment
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.ForumRepository
import javax.inject.Inject

class ForumRepositoryImpl @Inject constructor(private val forumRetrofitService: ForumRetrofitService) :
    ForumRepository {
    override suspend fun getPosts(): List<Post> =
        forumRetrofitService.getPosts().toPosts()

    override suspend fun getCommentsForPost(postId: Int): List<Comment> =
        forumRetrofitService.getCommentsForPost(postId).toComments()

    override suspend fun getUser(userId: Int): User =
        forumRetrofitService.getUser(userId).toUser()

}
