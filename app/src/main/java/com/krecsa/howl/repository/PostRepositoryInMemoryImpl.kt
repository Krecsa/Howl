package com.krecsa.howl.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krecsa.howl.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var posts = listOf(
        Post(
            id = 1,
            author = "Catherine",
            content = "Добро пожаловать в Howl! 🐺",
            published = "Сегодня в 23:00",
            likes = 6,
            likedByMe = false,
            shares = 3
        )
    )

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id.toInt()) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }
    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id.toInt()) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }
}