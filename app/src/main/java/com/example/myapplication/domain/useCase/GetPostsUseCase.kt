package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.repository.ForumRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(private val forumRepository: ForumRepository) {
    suspend operator fun invoke(): List<Post> {
        return forumRepository.getPosts()
    }
}