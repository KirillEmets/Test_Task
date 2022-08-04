package com.example.myapplication.data.di

import com.example.myapplication.data.apiService.ForumRetrofitService
import com.example.myapplication.data.repository.ForumRepositoryImpl
import com.example.myapplication.domain.repository.ForumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideForumRepository(forumRetrofitService: ForumRetrofitService): ForumRepository {
        return ForumRepositoryImpl(forumRetrofitService)
    }
}
