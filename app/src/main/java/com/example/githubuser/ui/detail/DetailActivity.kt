package com.example.githubuser.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.githubuser.GithubUser
import com.example.githubuser.R
import com.example.githubuser.data.response.DetailUserResponse
import com.example.githubuser.data.room.FavoriteEntity
import com.example.githubuser.databinding.ActivityDetailBinding
import com.example.githubuser.ui.ViewPagerAdapter
import com.example.githubuser.ui.favorite.FavoriteViewModel
import com.example.githubuser.ui.favorite.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel> {
        FavoriteViewModelFactory.getInstance(
            application
        )
    }
    private var isFavorite = false

    //TabLayout Title
    private val titleTab = intArrayOf(
        R.string.follower,
        R.string.following
    )

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
        const val EXTRA_FRAGMENT = "extra_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Intent
        val detailIntent = intent.getParcelableExtra<GithubUser>(EXTRA_DETAIL) as GithubUser

        //Observer
        detailViewModel.getDetailUser(detailIntent.login)
        detailViewModel.listUserDetail.observe(this) { detailUser ->
            setData(detailUser)
        }
        detailViewModel.isLoading.observe(this) { loading ->
            showLoading(loading)
        }
        favoriteViewModel.getFavorite(detailIntent.login).observe(this) { favoriteUser ->
            isFavorite = favoriteUser.isNotEmpty()

            if (favoriteUser.isEmpty()) {
                binding.fabFavoriteUser.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavoriteUser.context,
                        R.drawable.ic_favorite_border
                    )
                )
            } else {
                binding.fabFavoriteUser.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.fabFavoriteUser.context,
                        R.drawable.ic_favorite_fill
                    )
                )
            }
        }

        //FAB Binding
        binding.fabFavoriteUser.setOnClickListener {
            val favoriteEntity = FavoriteEntity(detailIntent.login, detailIntent.avatarUrl)
            if (isFavorite) {
                favoriteViewModel.delete(favoriteEntity)
                Toast.makeText(
                    this@DetailActivity,
                    "${detailIntent.login} is Deleted from your favorite",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                favoriteViewModel.insert(favoriteEntity)
                Toast.makeText(
                    this@DetailActivity,
                    "${detailIntent.login} is Added to your favorite",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Section View Pager Initiate
        val login = Bundle().apply { putString(EXTRA_FRAGMENT, detailIntent.login) }
        val sectionPagerAdapter = ViewPagerAdapter(this, login)
        binding.viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(titleTab[position])
        }.attach()
    }

    //Data detail
    private fun setData(listDetailUser: DetailUserResponse) {
        binding.apply {
            Glide.with(this@DetailActivity)
                .load(listDetailUser.avatarUrl)
                .circleCrop()
                .into(avProfile)
            profileName.text = listDetailUser.name
            username.text = listDetailUser.login
            followers.text = listDetailUser.followers.toString() + " Followers"
            following.text = listDetailUser.following.toString() + " Following"
        }
    }

    //Loading setter
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}