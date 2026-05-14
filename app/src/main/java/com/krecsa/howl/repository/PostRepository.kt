package com.krecsa.howl.repository

import androidx.lifecycle.LiveData
import com.krecsa.howl.dto.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
}