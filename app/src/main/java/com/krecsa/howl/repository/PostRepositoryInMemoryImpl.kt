package com.krecsa.howl.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krecsa.howl.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var posts = listOf(
        Post(
            id = 1,
            author = "krecsa",
            content = "Добро пожаловать в Howl! 🐺",
            published = "только что",
            likes = 0,
            likedByMe = false,
            shares = 0
        )
    )

    private var nextId = posts.first().id + 1

    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shares = it.shares + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        if (post.id == 0L) {
            posts = listOf(post.copy(id = nextId++)) + posts
        } else {
            posts = posts.map { if (it.id == post.id) post else it }
        }
        data.value = posts
    }
}