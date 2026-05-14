package com.krecsa.howl.viewmodel

import androidx.lifecycle.ViewModel
import com.krecsa.howl.repository.PostRepository
import com.krecsa.howl.repository.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)

}