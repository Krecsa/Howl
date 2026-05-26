package com.krecsa.howl.activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.krecsa.howl.R
import com.krecsa.howl.adapter.PostsAdapter
import com.krecsa.howl.databinding.ActivityMainBinding
import com.krecsa.howl.utils.AndroidUtils
import com.krecsa.howl.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(
            likeListener = { viewModel.likeById(it.id) },
            shareListener = { viewModel.shareById(it.id) },
            removeListener = { viewModel.removeById(it.id) },
            editListener = { viewModel.edit(it) }
        )

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                binding.content.setText(post.content)
                binding.content.requestFocus()
                AndroidUtils.showKeyboard(binding.content)
                binding.editPanel.visibility = View.VISIBLE
            } else {
                binding.content.setText("")
                binding.editPanel.visibility = View.GONE
            }
        }

        binding.save.setOnClickListener {
            val content = binding.content.text.toString()
            if (content.isBlank()) return@setOnClickListener
            viewModel.savePost(content)
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
        }

        binding.cancel.setOnClickListener {
            viewModel.cancelEdit()
            binding.content.setText("")
            binding.content.clearFocus()
            AndroidUtils.hideKeyboard(binding.content)
        }
    }
}