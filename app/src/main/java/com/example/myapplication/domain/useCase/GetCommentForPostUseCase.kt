package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.Comment
import com.example.myapplication.domain.repository.ForumRepository
import javax.inject.Inject

class GetCommentForPostUseCase @Inject constructor(private val forumRepository: ForumRepository) {
    suspend operator fun invoke(postId: Int): List<Comment> {
        return forumRepository.getCommentsForPost(postId)
    }
}