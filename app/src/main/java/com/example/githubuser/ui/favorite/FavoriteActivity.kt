package com.example.githubuser.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.GithubUser
import com.example.githubuser.data.room.FavoriteEntity
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.ui.UserAdapter

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(
            application
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //binding layout
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)

        favoriteViewModel.getAllFavorite().observe(this) { users: List<FavoriteEntity> ->
            val items = arrayListOf<GithubUser>()
            users.map {
                val item = GithubUser(login = it.username, avatarUrl = it.avatar_url)
                items.add(item)
            }
            binding.rvUser.adapter = UserAdapter(items)
        }

    }
}