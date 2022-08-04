package com.example.myapplication.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

// Compose is notoriously laggy in debug version, so build release for best performance
// (or ignore it, it doesn't lag too much)

@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel = hiltViewModel()) {
    val uiState by homeScreenViewModel.screenUiState.collectAsState(ScreenUiState())

    LazyColumn {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                text = "Click on post to load comments."
            )
        }

        items(uiState.posts.size) { index ->
            val post = uiState.posts[index]
            if (post.userName == null) {
                homeScreenViewModel.loadUserName(post.userId)
            }

            PostItem(post, onClick = {
                homeScreenViewModel.loadComments(post.postId)
            })
        }
    }

    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.errorMessage) {
        uiState.errorMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
fun PostItem(post: PostUIState, onClick: () -> Unit) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(onClick = onClick)
    ) {
        Column {
            Text(modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, text = post.title)
            Text(modifier = Modifier.padding(4.dp), text = "by ${post.userName ?: "..."}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(modifier = Modifier.padding(4.dp), text = post.body)

            post.comments?.let { comments ->
                Column() {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        fontWeight = FontWeight.Bold,
                        text = "Comments:"
                    )
                    comments.forEach {
                        CommentItem(it)
                    }
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: CommentUIState) {
    Column(Modifier.padding(start = 16.dp)) {
        Text(modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, text = comment.name)
        Text(modifier = Modifier.padding(4.dp), text = comment.email)
        Spacer(modifier = Modifier.height(4.dp))
        Text(modifier = Modifier.padding(4.dp), text = comment.body)
        Divider(Modifier.fillMaxWidth(0.8f))
    }
}
