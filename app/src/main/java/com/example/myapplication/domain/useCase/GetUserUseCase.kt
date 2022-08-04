package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.repository.ForumRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val forumRepository: ForumRepository) {
    suspend operator fun invoke(userId: Int): User {
        return forumRepository.getUser(userId)
    }
}