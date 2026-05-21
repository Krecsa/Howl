package com.krecsa.howl.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.krecsa.howl.dto.Post
import com.krecsa.howl.repository.PostRepository
import com.krecsa.howl.repository.PostRepositoryInMemoryImpl

private val empty = Post()

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()

    val edited = MutableLiveData(empty)
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun savePost(content: String) {
        edited.value?.let { post ->
            val trimmed: String = content.trim()


            if (post.content != trimmed) {
                repository.save(
                    post.copy(content = trimmed)
                )
            }
            edited.value = empty
        }
    }
}
