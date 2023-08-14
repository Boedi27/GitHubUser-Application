package com.example.githubuser.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.GithubUser
import com.example.githubuser.databinding.ItemUserBinding
import com.example.githubuser.ui.detail.DetailActivity

class FollowAdapter(private val listFollow: List<GithubUser>) :
    RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    class ViewHolder(var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val follow = listFollow[position]

        viewHolder.apply {
            binding.apply {
                Glide.with(root.context).load(follow.avatarUrl).circleCrop().into(avUserItem)
                tvUserItem.text = follow.login

                root.setOnClickListener {
                    val intent = Intent(root.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DETAIL, follow)
                    root.context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listFollow.size
    }
}