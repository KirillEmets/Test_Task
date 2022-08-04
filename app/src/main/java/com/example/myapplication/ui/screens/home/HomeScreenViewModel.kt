package com.example.myapplication.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Comment
import com.example.myapplication.domain.model.Post
import com.example.myapplication.domain.useCase.GetCommentForPostUseCase
import com.example.myapplication.domain.useCase.GetPostsUseCase
import com.example.myapplication.domain.useCase.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ScreenUiState(
    val posts: List<PostUIState> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val getCommentsForPostUseCase: GetCommentForPostUseCase,
    private val getUserUseCase: GetUserUseCase,
) : ViewModel() {

    private val loadedUserNames: MutableStateFlow<Map<Int, String>> =
        MutableStateFlow(emptyMap())

    private val pendingUserNames: MutableSet<Int> = mutableSetOf()

    private val loadedComments: MutableStateFlow<Map<Int, List<Comment>>> =
        MutableStateFlow(emptyMap())

    private val allPosts: MutableStateFlow<List<Post>> =
        MutableStateFlow(emptyList())

    private val errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)

    init {
        viewModelScope.launch {
            try {
                allPosts.value = getPostsUseCase()
            } catch (e: Exception) {
                errorMessage.value = "Failed to load posts."
            }
        }
    }

    val screenUiState = combine(
        loadedUserNames,
        loadedComments,
        allPosts,
        errorMessage
    ) { loadedUserNames, loadedComments, allPosts, errorMessage ->
        ScreenUiState(
            posts = allPosts.map { post ->
                PostUIState(
                    post.id,
                    post.userId,
                    loadedUserNames[post.userId],
                    post.title,
                    post.body,
                    loadedComments[post.id]?.map { it.toCommentUIState() }
                )
            },
            errorMessage
        )
    }


    /* The idea behind caching usernames is that usernames are contained in a separate endpoint,
        so would need to load username from server for every single post. It is much better to cache them and call
        getUser only once for each unique user.

        I also decided to not load all the users from the start, and instead load them as soon as they
        are visible on the screen, because there are potentially millions of users and posts, so they couldn't
        be loaded easily.
        */

    fun loadUserName(userId: Int) {
        if (pendingUserNames.contains(userId)) {
            return
        }

        pendingUserNames += userId
        viewModelScope.launch {
            val userName = try {
                 val user = getUserUseCase(userId)
                "${user.name}, ${user.username}"

            } catch (e: Exception) {
                errorMessage.value = "Failed to load username."
                "No name"
            }

            loadedUserNames.update { it + (userId to userName) }

            Log.i("TAG", "add username: ${userName}, size ${loadedUserNames.value.size}")
        }
    }

    fun loadComments(postId: Int) {
        if(loadedComments.value.containsKey(postId))
            return

        viewModelScope.launch {
            try {
                val comments = getCommentsForPostUseCase(postId)
                loadedComments.update { it + (postId to comments) }
            } catch (e: Exception) {
                errorMessage.value = "Failed to load comments."
            }
        }
    }
}
